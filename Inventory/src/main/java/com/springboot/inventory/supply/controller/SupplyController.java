package com.springboot.inventory.supply.controller;

import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.supply.dto.SupplyDetailsDto;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.service.SupplyResponseDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/supply")
public class SupplyController {

    private final SupplyResponseDtoService supplyResponseDtoService;
    private static final String SUPPLY_LIST_VIEW = "supplyList";
    private static final String SUPPLY_DETAILS_VIEW = "supplyDetails";

    @Autowired
    public SupplyController(SupplyResponseDtoService supplyResponseDtoService) {
        this.supplyResponseDtoService = supplyResponseDtoService;
    }

    @GetMapping("/list")
    public String getSupplyList(
            @RequestParam(required = false, defaultValue = "all") String selectedStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SupplyResponseDto> supplyResponseDtosPage;

        if ("null".equals(keyword)) {
            keyword = "";
        }

        if (StringUtils.hasText(keyword)) {
            if (!"all".equals(selectedStatus)) {
                supplyResponseDtosPage = supplyResponseDtoService.searchByKeywordAndStatus(keyword, SupplyStatusEnum.valueOf(selectedStatus), pageable);
            } else {
                supplyResponseDtosPage = supplyResponseDtoService.searchByKeyword(keyword, pageable);
            }
        } else {
            if ("all".equals(selectedStatus)) {
                supplyResponseDtosPage = supplyResponseDtoService.findAllByDeletedFalse(pageable);
            } else {
                supplyResponseDtosPage = supplyResponseDtoService.findDistinctByStatusAndDeletedFalse(SupplyStatusEnum.valueOf(selectedStatus), pageable);
            }
        }

        model.addAttribute("supplyResponseDtos", supplyResponseDtosPage.getContent());
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        model.addAttribute("totalPages", supplyResponseDtosPage.getTotalPages());
        model.addAttribute("selectedStatus", selectedStatus);
        model.addAttribute("statusList", SupplyStatusEnum.values());
        model.addAttribute("keyword", keyword);

        return SUPPLY_LIST_VIEW;
    }

    @GetMapping("/details/{id}")
    public String supplyDetails(@PathVariable("id") Long supplyId, Model model) {
        Optional<SupplyDetailsDto> supplyOptional = supplyResponseDtoService.getSupplyById(supplyId);
        supplyOptional.ifPresent(supply -> model.addAttribute("supply", supply));
        return supplyOptional.map(supply -> SUPPLY_DETAILS_VIEW).orElse(SUPPLY_LIST_VIEW);
    }

    @PostMapping("/delete/{supplyId}")
    public String deleteSupply(@PathVariable Long supplyId) {
        supplyResponseDtoService.deleteSupply(supplyId);
        return "redirect:/supply/list"; // 삭제 후 공급품 목록 페이지로 리다이렉트합니다. 실제 리다이렉트 경로는 상황에 따라 변경될 수 있습니다.
    }
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
