package com.springboot.inventory.user.service;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.ResponseEnum;
import com.springboot.inventory.common.enums.TokenType;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.common.jwt.JwtProvider;
import com.springboot.inventory.common.util.redis.RedisRepository;
import com.springboot.inventory.common.util.redis.RefreshToken;
import com.springboot.inventory.user.dto.SignInResultDto;
import com.springboot.inventory.user.dto.SignUpResultDto;
import com.springboot.inventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    @Autowired
    private RedisRepository redisRepository;

    // 회원가입
    @Transactional
    @Override
    public SignUpResultDto signUp(String email, String password, String name, String tel) {

        LOGGER.info("[UserServiceImpl - signUp]");

        if(userRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username(name)
                .tel(tel)
                .roles(UserRoleEnum.USER)
                .build();

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        LOGGER.info("[UserServiceImpl - signUp - savedUser]");
        if (!savedUser.getEmail().isEmpty()) {
            LOGGER.info("[savedUser - OK]");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[savedUser - FAIL]");
            setFailResult(signUpResultDto);
        }

        return signUpResultDto;
    }

    // 로그인
    @Transactional
    @Override
    public SignInResultDto signIn(String email, String password) throws RuntimeException {
        LOGGER.info("[UserServiceImpl - signIn]");
        User user = userRepository.getByEmail(email);

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }       // 로그인 실패 시

        getRefreshToken(user);

        String accessToken = jwtProvider.createToken(user.getEmail(), user.getRoles(), TokenType.ACCESS);

        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(accessToken)
                .build();

        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    // 로그아웃
    public ResponseEntity<String> logOut(String email, HttpServletRequest request, HttpServletResponse response) {
        deleteAllCookies(request, response);
        deleteRefreshToken(email);
        return ResponseEntity.ok("로그아웃 성공");
    }

    private void deleteRefreshToken(String email) {
        Optional<RefreshToken> refreshToken =redisRepository.findById(email);
        if(refreshToken.isPresent()){
            redisRepository.deleteById(email);
        }
    }

    private void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                ResponseCookie responseCookie = ResponseCookie.from(cookie.getName(), null).
                        path("/").
                        httpOnly(true).
                        sameSite("None").
                        secure(true).
                        maxAge(1).
                        build();
                response.addHeader("Set-Cookie", responseCookie.toString());
            }
        }
    }


    // 권한 변경
    @Transactional
    public ResponseEntity<String> grantRole(String email, UserRoleEnum roles) {
        User user = userRepository.getByEmail(email);

        UserRoleEnum grantedRole = roles == UserRoleEnum.USER ? UserRoleEnum.MANAGER : UserRoleEnum.USER;
        user.changeRole(grantedRole);
        return ResponseEntity.ok("권한 부여가 완료되었습니다.");
    }

    // 전체 유저 조회
    @Override
    @Transactional
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(ResponseEnum.SUCCESS.getCode());
        result.setMsg(ResponseEnum.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(ResponseEnum.FAIL.getCode());
        result.setMsg(ResponseEnum.FAIL.getMsg());
    }

    private void getRefreshToken(User user) {
        String createdRefreshToken = jwtProvider.createToken(user.getEmail(), user.getRoles(), TokenType.REFRESH);
        Optional<RefreshToken> refreshToken = redisRepository.findById(user.getEmail());
        long expiration = jwtProvider.REFRESH_TOKEN_TIME;

        if(refreshToken.isPresent()) {
            RefreshToken savedRefreshToken = refreshToken.get().updateToken(createdRefreshToken, expiration);
            redisRepository.save(savedRefreshToken);
        } else {
            RefreshToken refreshToSave = RefreshToken.builder()
                    .email(user.getEmail())
                    .refreshToken(createdRefreshToken)
                    .expiration(expiration)
                    .build();
            redisRepository.save(refreshToSave);
        }
    }

}
