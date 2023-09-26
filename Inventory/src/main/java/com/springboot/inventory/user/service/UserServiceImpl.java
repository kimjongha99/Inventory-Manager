package com.springboot.inventory.user.service;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.ResponseEnum;
import com.springboot.inventory.common.enums.TokenType;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.common.jwt.JwtProvider;
import com.springboot.inventory.common.util.redis.RedisRepository;
import com.springboot.inventory.common.util.redis.RefreshToken;
import com.springboot.inventory.user.dto.*;
import com.springboot.inventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    public SignUpResultDto signUp(SignUpRequestDto signUpRequestDto) {

        LOGGER.info("[UserServiceImpl - signUp]");

//        if (userRepository.existsByEmail(email)) {
//            throw new IllegalStateException("이미 존재하는 이메일입니다.");
//        }
        User user = User.builder()
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .username(signUpRequestDto.getUsername())
                .tel(signUpRequestDto.getTel())
                .team(signUpRequestDto.getTeam())
                .roles(UserRoleEnum.USER)
                .deleted(false)
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
    public SignInResultDto signIn(String email, String password, HttpServletRequest request, HttpServletResponse response) throws RuntimeException, UnsupportedEncodingException {
        LOGGER.info("[UserServiceImpl - signIn]");
        User user = userRepository.getByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();   // 로그인 실패 시
        }

        getAccessToken(user, response);
        getRefreshToken(user, request, response);

        SignInResultDto signInResultDto = SignInResultDto.builder().build();
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    // 로그아웃
    @Override
    @Transactional
    public void logOut(String email, HttpServletRequest request, HttpServletResponse response) {
        deleteAllCookies(request, response);
        deleteRefreshToken(email);
        ResponseEntity.ok("로그아웃 성공");
    }


    // 권한 변경
    @Override
    @Transactional
    public ResponseEntity<String> grantRole(String email, UserRoleEnum roles) {
        User user = userRepository.getByEmail(email);

        if (user != null) {
            UserRoleEnum currentRole = user.getRoles();

            // 현재 권한이 USER이면 MANAGER로, MANAGER이면 USER로 변경
            UserRoleEnum newRole = (currentRole == UserRoleEnum.USER) ? UserRoleEnum.MANAGER : UserRoleEnum.USER;

            user.changeRole(newRole);
            return ResponseEntity.ok("권한 부여가 완료되었습니다.");
        } else {
            // 사용자를 찾을 수 없을 때 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    // USER 찾기
    public List<User> getUsersByUserRole() {
        // 역할(role)이 USER인 사용자만 필터링하여 반환
        return userRepository.findByRolesAndDeletedFalse(UserRoleEnum.USER);
    }


    // 이메일로 유저 찾기
    public User getUser(String email) {
        System.out.println("service: " + email);
        User user = userRepository.getByEmail(email);
        System.out.println("repository " + user.getRoles());
        return user;
    }

    @Transactional
    public List<UserInfoDto> findAllUser() { // 모든 USER를 보여준다.(MANAGER,ADMIN)
        List<User> userList = getUsersByUserRole();
        List<UserInfoDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            if (!user.isDeleted()) { // deleted가 false인 사용자만 처리
                userDtoList.add(UserInfoDto.toDto(user));
            }
        }

        return userDtoList;
    }

    // 이메일 중복 확인
    public boolean doublecheck(String email) {
        // 이메일 중복 확인: 데이터베이스에서 해당 이메일로 사용자를 찾아봄
        boolean checkemail = userRepository.existsByEmail(email);

        if (checkemail) {
            // 이메일이 이미 존재하는 경우
            System.out.println("중복된 값입니다. 다시 입력해주세요.");
            return true;
        } else {
            // 이메일이 존재하지 않는 경우
            System.out.println("사용할 수 있는 이메일입니다.");
            return false;
        }
    }

    // 개인 조회
    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    // 회원 탈퇴
    @Override
    @Transactional
    public void deleteUser(String email, HttpServletRequest request, HttpServletResponse response) {

        userRepository.deleteByEmail(email);
        deleteRefreshToken(email);
        deleteAllCookies(request, response);
    }

    @Override
    @Transactional
    public void delete(String email){
        userRepository.deleteByEmail(email);
        deleteRefreshToken(email);
    }
    // 팀 설정 업데이트하기
    @Transactional
    public void updateTeam(String email, String newTeam) {
        Optional<User> byUser = userRepository.findByEmail(email);
        if(byUser.isPresent()){
            User user = byUser.get();
            user.updateTeam(newTeam);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }

    // 전체 유저 조회(ADMIN용)
    @Override
    public List<UserInfoDto> findAllUserForAdmin(String adminEmail) {
        List<User> userList = userRepository.findAllByDeletedIsFalse();
        List<UserInfoDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            // 현재 유저를 제외한 다른 유저만 추가
            if (!user.getEmail().equals(adminEmail)) {
                userDtoList.add(UserInfoDto.toDto(user));
            }
        }
        return userDtoList;
    }

    // 비밀번호 수정
    @Override
    public ResponseEntity<String> changePassword(ChangePasswordDto changePasswordDto, User user) {
        User chUser = userRepository.getByEmail(user.getEmail());
        chUser.changePassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        userRepository.save(chUser);
        return ResponseEntity.ok("비밀번호 변경 완료.");
    }

    // 회원 정보 수정
    @Override
    public ResponseEntity<String> updateUser(UpdateUserDto updateUserDto, User user) {
        User upUser = userRepository.getByEmail(user.getEmail());
        upUser.updateUser(updateUserDto.getUsername(), updateUserDto.getTel());
        userRepository.save(upUser);
        return ResponseEntity.ok("회원 정보 수정 완료.");
    }

    @Override
    public ResponseEntity<String> checkPassword(String email, String password) {
        User user = userRepository.getByEmail(email);

        if(passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.ok("비밀번호 체크 성공");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
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

    private  void getAccessToken(User user, HttpServletResponse response) throws UnsupportedEncodingException {
        String createdAccessToken = jwtProvider.createToken(user.getEmail(), user.getRoles(), TokenType.ACCESS);

        ResponseCookie cookie = ResponseCookie.from(
                JwtProvider.AUTHORIZATION_HEADER,
                URLEncoder.encode(createdAccessToken, "UTF-8"))
                .path("/")
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .maxAge(JwtProvider.ACCESS_TOKEN_TIME)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void getRefreshToken(User user, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        String createdRefreshToken = jwtProvider.createToken(user.getEmail(), user.getRoles(), TokenType.REFRESH);

        ResponseCookie cookie = ResponseCookie.from(JwtProvider.REFRESH_HEADER, URLEncoder.encode(createdRefreshToken, "UTF-8"))
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(JwtProvider.REFRESH_TOKEN_TIME)
                .build();
        httpServletResponse.addHeader("Set-Cookie", cookie.toString());

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
