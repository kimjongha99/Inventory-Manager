package com.springboot.inventory.supply.controller;

import com.springboot.inventory.supply.dto.SupplyDTO;
import com.springboot.inventory.supply.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/supply")
@RequiredArgsConstructor
public class SupplyCreateUpdateController {

    private final SupplyService supplyService;

    @GetMapping("")
    public  String home() {
        return "home";
    }

    @GetMapping("/create")
    public String createSupplyForm(Model model) {
        SupplyDTO supplyDTO = new SupplyDTO(); // SupplyDTO 객체 생성
        model.addAttribute("supplyDTO", supplyDTO); // 모델에 supplyDTO 추가
        return "supplyCreate"; // supplyCreate.html 템플릿을 렌더링
    }

    @PostMapping("/create")
    public String createSupply(
            @ModelAttribute @Valid SupplyDTO supplyDTO) throws Exception {
        supplyService.createSupply(supplyDTO);
        return "redirect:/supply/list";
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
        return "redirect:/supply/list";
    }

}

