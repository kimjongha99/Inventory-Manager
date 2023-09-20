package com.springboot.inventory.common;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
public class AllPageController {

    @GetMapping("/dashboard")
    public  String maindashboard() {
        return "dashboard/maindashboard";
    }

}
