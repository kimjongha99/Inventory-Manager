package com.springboot.inventory.supply.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupplyController {

    @GetMapping(value = "/register-supply")
    public String supplyRegisterPage() {

        return "/supply/SupplyRegisterPage";
    }

}
