package com.springboot.inventory.supply.controller;

import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.service.SupplyResponseDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/supply")
public class SupplyController {

    private final SupplyResponseDtoService supplyResponseDtoService;

    @GetMapping("/list")
    public String getSupplyByStatus(
            @RequestParam(required = false, defaultValue = "all") String selectedStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SupplyResponseDto> supplyResponseDtosPage;

        if("all".equals(selectedStatus)){
            supplyResponseDtosPage = supplyResponseDtoService.findAllByDeletedFalse(pageable);
        } else {
            supplyResponseDtosPage = supplyResponseDtoService.findDistinctByStatusAndDeletedFalse(SupplyStatusEnum.valueOf(selectedStatus), pageable);
        }

        List<SupplyStatusEnum> statusList = Arrays.asList(SupplyStatusEnum.values());

        model.addAttribute("supplyResponseDtos", supplyResponseDtosPage.getContent());
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        model.addAttribute("totalPages", supplyResponseDtosPage.getTotalPages());
        model.addAttribute("selectedStatus", selectedStatus);
        model.addAttribute("statusList", statusList);

        return "supplyList";
    }

    @Autowired
    public SupplyController(SupplyResponseDtoService supplyResponseDtoService) {
        this.supplyResponseDtoService = supplyResponseDtoService;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
