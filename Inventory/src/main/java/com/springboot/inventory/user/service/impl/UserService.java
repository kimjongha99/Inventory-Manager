package com.springboot.inventory.user.service.impl;


import com.springboot.inventory.user.dto.UserDTO;
import com.springboot.inventory.user.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO registerUser(UserDTO user);

}
