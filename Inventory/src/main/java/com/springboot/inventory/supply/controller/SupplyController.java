package com.springboot.inventory.Supply.controller;

import com.springboot.inventory.Supply.dto.SupplyResponseDto;
import com.springboot.inventory.Supply.service.SupplyResponseDtoService;
import com.springboot.inventory.common.enums.SupplyStatusEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/")
public class SupplyController {

    private final SupplyResponseDtoService supplyResponseDtoService;
    @GetMapping("/getSupplyByStatus")
    public String getSupplyByStatus(@RequestParam("selectedStatus") String selectedStatus, Model model) {
        // 선택된 상태에 따라 Supply 조회 로직을 수행
        System.out.println("selectedStatus: " + selectedStatus);
        if(selectedStatus.equals("all")){
            System.out.println(selectedStatus);
            return "redirect:/supply/admin/list";

        } else {
            List<SupplyResponseDto> supplyResponseDtos = supplyResponseDtoService.findDistinctByStatusAndDeletedFalse(SupplyStatusEnum.valueOf(selectedStatus));
            List<SupplyStatusEnum> statusList = Arrays.asList(SupplyStatusEnum.values());

            model.addAttribute("selectedStatus", selectedStatus); // 선택된 상태를 모델에 추가
            model.addAttribute("supplyResponseDtos", supplyResponseDtos);
            model.addAttribute("statusList", statusList);
            return "supplyList"; // Supply 목록을 표시하는 템플릿 이름
        }
    }
    @Autowired
    public SupplyController(SupplyResponseDtoService supplyResponseDtoService) {
        this.supplyResponseDtoService = supplyResponseDtoService;
    }
    @GetMapping("/")
    public String index() {
        return "index"; // Thymeleaf 템플릿의 이름을 반환합니다. 여기서는 "index.html"을 찾을 것입니다.
    }

    @GetMapping("supply/admin/list")
    public String getAllSupplyDetails(Model model) {
        List<SupplyResponseDto> supplyResponseDtos = supplyResponseDtoService.findByDeletedFalse();
        List<SupplyStatusEnum> statusList = Arrays.asList(SupplyStatusEnum.values());

        model.addAttribute("supplyResponseDtos", supplyResponseDtos);
        model.addAttribute("statusList", statusList);
        model.addAttribute("selectedStatus", "USING"); // 초기 상태를 "all"로 설정
        return "supplyList"; // Supply 목록을 표시하는 Thymeleaf 템플릿 파일 이름
    }




}