package com.springboot.inventory.supply.controller;

import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.service.SupplyResponseDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/supply")
public class SupplyController {

    private SupplyResponseDtoService supplyResponseDtoService;

    @Autowired
    public SupplyController(SupplyResponseDtoService supplyResponseDtoService) {
        this.supplyResponseDtoService = supplyResponseDtoService;
    }

    @GetMapping("/admin/list")
    public String getAllSupplyDetails(Model model) {
        List<SupplyResponseDto> supplyResponseDtos = supplyResponseDtoService.getAllSupplyResponseDtos();
        model.addAttribute("supplyResponseDtos", supplyResponseDtos);
        return "supplyList"; // Supply 목록을 표시하는 Thymeleaf 템플릿 파일 이름
    }
}