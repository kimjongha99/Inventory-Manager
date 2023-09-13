package com.springboot.inventory.user.controller;

import com.springboot.inventory.user.dto.UserDTO;
import com.springboot.inventory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    // 생성자 주입 , 스프링에서 제공해주는 기능
    private final UserService userService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/user/save")  //getmapping 은 (?) ?가 경로로 들어올때 실행
    public String saveForm() {
        return "save";
    }

    @PostMapping("/user/save")
    public String save(@ModelAttribute UserDTO userDTO) {
        userService.save(userDTO);
        return "login";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO loginResult = userService.login(userDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("username", loginResult.getUsername());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/user/")  //링크를 클릭하는 방식은 무조건 Get이다.
    public String findAll(Model model) {
        List<UserDTO> userDTOList = userService.findAll(); // 회원은 여러명이므로 list로 받는다.
        // 어떠한 html로 가져갈 데이터가 있다면 model사용 (스프링에서 제공하는 기능)
        model.addAttribute("userList", userDTOList);
        return "list";
    }

    @GetMapping("/user/{id}") //경로상에 있는 id를 받아온다 (pathvariable)
    public String findById(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.findById(id); //조회하는 데이터가 1개이므로 dto로 받아온다.
        model.addAttribute("user", userDTO);
        return "detail";
    }

    @GetMapping("/user/update")
    public String updateForm(HttpSession session, Model model) {
        String myUsername = (String) session.getAttribute("username"); //String으로 강제변환
        UserDTO userDTO = userService.updateForm(myUsername);
        model.addAttribute("updateUser", userDTO);
        return "update";
    }

    @PostMapping("/user/update")
    public String update(@ModelAttribute UserDTO userDTO) {
        userService.update(userDTO);
        // redirect 내 정보 수정하고 수정 완료 된 상세페이지 띄우기
        return "redirect:/user/" + userDTO.getId();
    }

    @GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/user/";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate(); //세션을 무효화한다
        return "index"; //로그아웃 하고 메인으로 간다
    }
//
//    @PostMapping("/user/email-check")
//    public @ResponseBody String emailCheck(@RequestParam("username") String userName) {
//        System.out.println("userName = " + userName);
//        String checkResult = userService.emailCheck(userName);
//        return checkResult;
////        if (checkResult != null) {
////            return "ok";
////        } else {
////            return "no";
////        }
//    }

}
