package com.springboot.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class simplecontroller {
    @GetMapping("/test")
    public String test(){
        System.out.println("asdasd");
        return "index";//주석
    }
}
