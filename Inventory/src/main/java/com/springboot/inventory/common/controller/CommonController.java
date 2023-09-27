package com.springboot.inventory.common.controller;

import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.common.security.UserDetailsImpl;
import com.springboot.inventory.supply.dto.SupplyDto;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.service.SupplyResponseDtoService;
import com.springboot.inventory.supply.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommonController {

    private final SupplyResponseDtoService supplyResponseDtoService;

    private final SupplyService supplyService;
    private static final String SUPPLY_LIST_VIEW = "/dashboard/managerdashboard";

    @GetMapping("/managerdashboard")
    public String dashBoard(
            @RequestParam(required = false, defaultValue = "all") String selectedStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
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

    @GetMapping("/dashboard")
    public String getSupplyUserByCategory(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model)
    {
        Long userId = userDetails.getUser().getUserId();
        List<LargeCategory> largeCategories = supplyService.getLargeCategories(); // LargeCategory 목록 가져오기
        Map<LargeCategory, List<SupplyDto>> supplyByCategoryMap = supplyService.getSupplyUserByCategory(userId);
        model.addAttribute("largeCategories", largeCategories);
        model.addAttribute("supplyByCategoryMap", supplyByCategoryMap);
        return "/dashboard/maindashboard";
    }



}
