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

    private final SupplyService supplyService;

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
