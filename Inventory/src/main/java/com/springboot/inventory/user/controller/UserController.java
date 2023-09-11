package com.springboot.inventory.user.controller;

import com.springboot.inventory.user.dto.SignInRequestDTO;
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


    @GetMapping(value = "/")
    public String indexView() {
        return "LandingPage";
    }


    @GetMapping(value = "/signup")
    public String signUpPage(Model model) {

        UserDTO userDTO = new UserDTO();

        model.addAttribute("userDTO", userDTO);

        return "SignUpPage";
    }

    @PostMapping(value = "/signup")
    // @Valid, BindingResult bindingResult 사용 예정
    public String signUp(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {

        userService.registerUser(userDTO);

        return "LandingPage";
    }

    @GetMapping(value = "/signin")
    public String signInPage(Model model) {

        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();

        model.addAttribute("signInRequestDTO", signInRequestDTO);

        return "SignInPage";
    }

    @PostMapping(value = "/signin")
    public String signIn(@ModelAttribute("signInRequestDTO") SignInRequestDTO signInRequestDTO, Model model) {

        System.out.println("로그인 컨트롤러");
        
        if(userService.loginUser(signInRequestDTO).getResult()) {
            System.out.println("로그인 성공");
            return "LandingPage";
        }

        return "SignInPage";
    }
}
