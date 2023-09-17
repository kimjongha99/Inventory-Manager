package com.springboot.inventory.supply.controller;

import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SupplyController {
    @Autowired
    private SupplyService supplyService;

    @GetMapping("/supplies")
    public String listSupplies(Model model) {
        List<SupplyResponseDto> supplies = supplyService.getAll(); // Supply 데이터를 가져오는 서비스 메서드를 호출
        model.addAttribute("supplies", supplies); // 모델에 Supply 리스트를 추가
        return "supplyList"; // supply-list.html 템플릿을 반환
    }
}