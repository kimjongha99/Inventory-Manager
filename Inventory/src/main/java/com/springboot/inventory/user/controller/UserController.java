package com.springboot.inventory.user.controller;

import com.springboot.inventory.user.dto.UserDTO;
import com.springboot.inventory.user.service.impl.UserService;

//
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/index")
    public String indexView() {
        return "index";
    }


    @GetMapping("/register")
    public String signUpPage(Model model) {

        UserDTO userDTO = new UserDTO();

        model.addAttribute("userDTO", userDTO);

        return "signUp";
    }

    @PostMapping("/register")
    // @Valid, BindingResult bindingResult 사용 예정
    public String signUp(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {

        userService.registerUser(userDTO);

        return "index";
    }

}
