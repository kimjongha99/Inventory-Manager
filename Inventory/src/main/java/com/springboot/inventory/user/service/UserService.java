package com.springboot.inventory.user.service;


import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.user.dto.SignInRequestDTO;

import com.springboot.inventory.user.dto.UserDTO;
import com.springboot.inventory.user.dto.UserResponseDTO;


public interface UserService {

    ResponseDTO<UserResponseDTO> registerUser(UserDTO userDTO);

    ResponseDTO<String> loginUser(SignInRequestDTO signInRequestDTO);

}
