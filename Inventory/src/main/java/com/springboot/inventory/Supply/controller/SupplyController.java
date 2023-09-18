package com.springboot.inventory.Supply.controller;

import com.springboot.inventory.Supply.dto.SupplyDTO;
import com.springboot.inventory.Supply.service.SupplyService;
import com.springboot.inventory.category.service.CategoryService;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/supply")
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @GetMapping("")
    public  String home() {
        return "home";
    }

    @GetMapping("/create")
    public String createSupplyForm(Model model) {
        SupplyDTO supplyDTO = new SupplyDTO(); // SupplyDTO 객체 생성
        model.addAttribute("supplyDTO", supplyDTO); // 모델에 supplyDTO 추가
        return "supply"; // supply.html 템플릿을 렌더링
    }

    @PostMapping("/create")
    public String createSupply(
            @ModelAttribute @Valid SupplyDTO supplyDTO) throws Exception {
        supplyService.createSupply(supplyDTO);
        return "redirect:/supply";
    }

    @GetMapping("/update/{supplyId}")
    public String updateSupplyForm(@PathVariable Long supplyId , Model model) {
        SupplyDTO supplyDTO = new SupplyDTO(); // SupplyDTO 객체 생성
        model.addAttribute("supplyDTO", supplyDTO); // 모델에 supplyDTO 추가
        model.addAttribute("supplyId", supplyId); // 모델에 supplyId 추가
        return "supplyUpdate"; // supplyUpdate.html 템플릿을 렌더링
    }

    @PostMapping("/update/{supplyId}")
    public String updateSupply (
            @PathVariable("supplyId") Long supplyId,
            @ModelAttribute @Valid SupplyDTO supplyDTO) throws Exception {
       supplyService.updateSupply(supplyId, supplyDTO) ;
        return "redirect:/supply";
    }

}

