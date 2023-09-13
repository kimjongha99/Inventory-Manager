package com.springboot.inventory.user.service;

import com.springboot.inventory.user.dto.SignInResultDto;
import com.springboot.inventory.user.dto.SignUpResultDto;

public interface UserService {

    SignUpResultDto signUp(String email, String password, String name, String tel);
    SignInResultDto signIn(String email, String password) throws RuntimeException;
}
