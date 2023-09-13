package com.springboot.inventory.user.controller;

import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.user.dto.SignInRequestDTO;
import com.springboot.inventory.user.dto.UserDTO;
import com.springboot.inventory.user.service.impl.UserService;

//
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return createModelAndView("LandingPage");
    }

    @GetMapping(value = "/signup")
    public ModelAndView signUpPage() {
        ModelAndView mv = createModelAndView("SignUpPage");
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
        ModelAndView mv = createModelAndView("SignInPage");
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

        /*
        따라서 클라이언트 웹 브라우저에 토큰이 저장되는 것은 서버에서 쿠키를 생성되
        고 응답을 클라이언트에게 보내면서 이루어지며, 직접적으로
        HttpServletResponse를 반환하는 코드가 없어도 Spring MVC가 내부적으
        로 처리합니다.
        */

        return "redirect:/signin";
    }

    @GetMapping("/master")
    public ModelAndView confidentialPage1() {
        return createModelAndView("TestingMaseterPage");
    }

    @GetMapping("/admin")
    public ModelAndView confidentialPage2() {
        return createModelAndView("TestingAdminPage");
    }

    @GetMapping("/user")
    public ModelAndView confidentialPage3() {
        return createModelAndView("TestingUserPage");
    }

}
