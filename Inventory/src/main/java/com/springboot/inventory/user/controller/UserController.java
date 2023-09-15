package com.springboot.inventory.user.controller;

import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.user.dto.SignInRequestDTO;
import com.springboot.inventory.user.dto.UserDTO;
import com.springboot.inventory.user.service.UserService;

//
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 공통적으로 사용되는 ModelAndView를 생성하는 메소드
    private ModelAndView createModelAndView(String view) {
        return new ModelAndView(view);
    }

    @GetMapping(value = "/")
    public ModelAndView indexView() {
        return createModelAndView("users/LandingPage");
    }

    @GetMapping(value = "/signup")
    public ModelAndView signUpPage() {
        ModelAndView mv = createModelAndView("users/SignUpPage");
        mv.addObject("userDTO", new UserDTO());
        return mv;
    }

    @PostMapping(value = "/signup")
    public ModelAndView signUp(@ModelAttribute("userDTO") UserDTO userDTO) {
        userService.registerUser(userDTO);
        return createModelAndView("redirect:/");
    }

    @GetMapping(value = "/signin")
    public ModelAndView signInPage() {
        ModelAndView mv = createModelAndView("users/SignInPage");
        mv.addObject("signInRequestDTO", new SignInRequestDTO());
        return mv;
    }

    @PostMapping(value = "/signin")
    public String signIn(@ModelAttribute("signInRequestDTO") SignInRequestDTO signInRequestDTO,
                         HttpServletResponse res) {
        ResponseDTO<String> response = userService.loginUser(signInRequestDTO);

        if (response.getResult()) {
            Cookie cookie = new Cookie("Authentication", response.getData());

            cookie.setPath("/");
            cookie.setHttpOnly(true);

            res.addCookie(cookie);
            return "redirect:/";
        }

        return "redirect:/signin";
    }

    @GetMapping("/master")
    public ModelAndView confidentialPage1() {
        return createModelAndView("users/TestingMaseterPage");
    }

    @GetMapping("/admin")
    public ModelAndView confidentialPage2() {
        return createModelAndView("users/TestingAdminPage");
    }

    @GetMapping("/user")
    public ModelAndView confidentialPage3() {
        return createModelAndView("users/TestingUserPage");
    }

}
