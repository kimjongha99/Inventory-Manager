package com.springboot.inventory.supply.controller;

import com.springboot.inventory.supply.domain.entity.Supply;
import com.springboot.inventory.supply.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @GetMapping(value = "/")
    public String list(Model model) {
        List<Supply> supply = supplyService.supplyList();
        model.addAttribute("supply", supply);
        return "/member/login";
    }

}