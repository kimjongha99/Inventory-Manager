package com.springboot.inventory.user.service;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.user.dto.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {


    SignUpResultDto signUp(String email, String password, String name, String tel);
    SignInResultDto signIn(String email, String password) throws RuntimeException;
    ResponseEntity<String> logOut(String email, HttpServletRequest request, HttpServletResponse response);
    ResponseEntity<String> grantRole(String email, UserRoleEnum roles);
    List<UserInfoDto> findAllUser();
    Optional<User> findByEmail(String email);
    void deleteUser(String email, HttpServletRequest request, HttpServletResponse response);
    List<UserInfoDto> findAllUserForAdmin(String Email);    // 전체 유저 조회(ADMIN)
    ResponseEntity<String> changePassword(ChangePasswordDto changePasswordDto, User user);
    ResponseEntity<String> updateUser(UpdateUserDto updateUserDto, User user);
}
