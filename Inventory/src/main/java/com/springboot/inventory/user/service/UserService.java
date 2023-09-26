package com.springboot.inventory.user.service;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.user.dto.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {


    SignUpResultDto signUp(SignUpRequestDto signUpRequestDto);
    SignInResultDto signIn(String email, String password, HttpServletRequest request, HttpServletResponse response) throws RuntimeException, UnsupportedEncodingException;
    void logOut(String email, HttpServletRequest request, HttpServletResponse response);
    ResponseEntity<String> grantRole(String email, UserRoleEnum roles);
    List<UserInfoDto> findAllUser(); // 전체 목록 조회 (MANAGER)
    List<User> getUsersByUserRole(); // 유저 롤로 조회

    User getUser(String email); // 역할 찾기
    boolean doublecheck(String email) ; // 이메일 중복확인
    Optional<User> findByEmail(String email);
    void deleteUser(String email, HttpServletRequest request, HttpServletResponse response);

    void delete(String email); // MANAGER가 유저를 삭제
    List<UserInfoDto> findAllUserForAdmin(String Email);    // 전체 유저 조회(ADMIN)
    ResponseEntity<String> changePassword(ChangePasswordDto changePasswordDto, User user);
    ResponseEntity<String> updateUser(UpdateUserDto updateUserDto, User user);
    void updateTeam(String email, String team);  // 팀 업데이트 (MANAGER,ADMIN)
    ResponseEntity<String> checkPassword(String email, String password);

}
