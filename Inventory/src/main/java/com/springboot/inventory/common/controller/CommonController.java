package com.springboot.inventory.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("/main/dashboard")
    public String dashBoard() {
        return "/dashboard/maindashboard";
    }

}
