package com.springboot.inventory.user.controller;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.user.dto.UserAllDto;
import com.springboot.inventory.user.dto.UserDto;
import com.springboot.inventory.user.dto.UserUpdateDto;
import com.springboot.inventory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

//@RestController
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET 방식
    @GetMapping("/") // 처음 시작화면
    public String index(){
        return "index";
    }

    @GetMapping("/signup") // 회원가입 페이지로 이동
    public String signup(){

        return "signup";

    }
    @GetMapping("/loginform") // 로그인 폼 페이지로 이동
    public String loginForm(){
        return "loginform";
    }

    @GetMapping("/userlist")
    public String findAll(Model model){
        List<UserDto> userDtoList = userService.findAll();
        model.addAttribute("userlist",userDtoList);
        return "userlist";
    }

    @GetMapping("{username}")
    public String findUser(@PathVariable String username, Model model){
        UserAllDto userDto = userService.findUser(username);
        model.addAttribute("finduser", userDto);
        return "detail";
    }

    @GetMapping("/updateform")
    public String updateform(HttpSession httpSession, Model model) {
        String username = (String) httpSession.getAttribute("loginName");
        UserAllDto userDto = userService.updateForm(username);


        model.addAttribute("updateuser", userDto);

        return "updateform";
    }

    @GetMapping("/delete/{username}")
    public String delete(@PathVariable String username){
        userService.delete(username);
        return "redirect:/user/userlist";
    }

    // POST 방식
    @PostMapping("/signup") // 회원가입 및 데이터베이스에 저장
    public String signup(@ModelAttribute UserDto userDto){
        userService.signUp(userDto);
        return "redirect:/";
    }
    @PostMapping("/login") // 로그인 시도
    public String login(@ModelAttribute UserDto userDto, HttpSession session){
        UserDto loginResult = userService.login(userDto);

        if(loginResult != null)
        {
            session.setAttribute("loginName", loginResult.getUsername());

            return "main";
            // login 성공
        } else{
            // login 실패
            return "redirect:/";
        }
    }

    @PostMapping("/update")
    public String update(@ModelAttribute UserUpdateDto userDto){

        userService.update(userDto);
//        return "redirect:/user/"+ userDto.getUsername();
        return "redirect:/user/"+userDto.getUsername();
    }

}
