package com.springboot.inventory.user.controller;

import com.springboot.inventory.user.dto.SignInExDto;
import com.springboot.inventory.user.dto.SignInResultDto;
import com.springboot.inventory.user.dto.SignUpResultDto;
import com.springboot.inventory.user.service.UserService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sign-api")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/sign-in")
//    public SignInResultDto signIn(@ApiParam(value = "Email", required = true) @RequestParam String email, @ApiParam(value = "Password", required = true)
//            @RequestParam String password) throws RuntimeException {
//    LOGGER.info("[UserController - signIn]");
//    SignInResultDto signInResultDto = userService.signIn(email, password);
//
//        if (signInResultDto.getCode() == 0) {
//        LOGGER.info("[UserController - signIn 완료.] email : {}, token : {}", email, signInResultDto.getToken());
//    }
//        return signInResultDto;
//  }


    @GetMapping("/sign-in")
    public String signInPage(Model model) {

        SignInExDto req = new SignInExDto();

        model.addAttribute("signInExDto", req);

        return "signInPage";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute ("signInExDto")SignInExDto sign, HttpServletResponse response)
    {
        String email = sign.getEmail();
        String password = sign.getPassword();

        SignInResultDto result = userService.signIn(email, password);

        if (result != null) {
            Cookie cookies = new Cookie("Authorization", result.getToken());

            cookies.setPath("/");
            cookies.setHttpOnly(true);
            cookies.setMaxAge(300);

            response.addCookie(cookies);

            return "LandingPage";
        }

        return "redirect:/sign-api/sign-in";
    }

    @GetMapping("/exception")
    public String exceptionTest() {
        return "ExceptionPage";
    }


//    @PostMapping(value = "/sign-up")
//    public SignUpResultDto signUp(@ApiParam(value = "Email", required = true) @RequestParam String email,
//                                  @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
//                                  @ApiParam(value = "이름", required = true) @RequestParam String username,
//                                  @ApiParam(value = "전화번호", required = true) @RequestParam String tel) {
//        LOGGER.info("[UserController - signUp]");
//        SignUpResultDto signUpResultDto = userService.signUp(email, password, username, tel);
//        LOGGER.info("[UserController - signUp 완료.]");
//
//        return signUpResultDto;
//    }


}
