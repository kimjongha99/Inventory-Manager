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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                        .role(UserRoleEnum.USER)
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

    @Transactional
    @Override
    public SignInResultDto signIn(String email, String password) throws RuntimeException {
        LOGGER.info("[UserServiceImpl - signIn]");
        User user = userRepository.getByEmail(email);

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }

        getRefreshToken(user);

        String accessToken = jwtProvider.createToken(String.valueOf(user.getEmail()), user.getRole(), TokenType.ACCESS);

        SignInResultDto signInResultDto = SignInResultDto.builder()
                        .token(accessToken)
                        .build();
        setSuccessResult(signInResultDto);

        return signInResultDto;
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
        String createdRefreshToken = jwtProvider.createToken(user.getEmail(), user.getRole(), TokenType.REFRESH);
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
