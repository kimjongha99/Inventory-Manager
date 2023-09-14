package com.springboot.inventory.user.service;

import com.springboot.inventory.common.entity.Group;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.user.dto.UserAllDto;
import com.springboot.inventory.user.dto.UserDto;
import com.springboot.inventory.user.dto.UserResponseDto;
import com.springboot.inventory.user.dto.UserUpdateDto;

import java.util.List;

public interface UserService {

    public User getUser(String username); // username으로 정보 조회

    public void signUp(UserDto userDto); // 회원가입

    public UserDto login(UserDto userDto); // 로그인

    public List<UserDto> findAll(); // 회원목록 조회

     public UserAllDto findUser(String username); // 회원정보 상세보기

     public UserAllDto updateForm(String username); // 회원정보 업데이트 시 -> 업데이트 폼으로 가기 전 세션의 아이디를 조회하여 정보 가져오기

     public void update(UserUpdateDto userDto);

     public void delete(String username);
}
