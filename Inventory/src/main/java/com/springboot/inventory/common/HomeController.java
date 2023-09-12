package com.springboot.inventory.common;

import com.springboot.inventory.user.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if(userDetails == null){
            return "index";
        }else{
            model.addAttribute("nickname", userDetails.getUsername());
            return "index";
        }
    }
}
