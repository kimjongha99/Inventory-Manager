package com.springboot.inventory.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashController {


    @GetMapping("/dashboard")
    public String dash(){
        return "~~~/";
    }
}
