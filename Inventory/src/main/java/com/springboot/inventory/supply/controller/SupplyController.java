package com.springboot.inventory.supply.controller;

import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.supply.dto.SupplyDetailsDto;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.service.SupplyResponseDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/supply")
public class SupplyController {

    private final SupplyResponseDtoService supplyResponseDtoService;
    private static final String SUPPLY_LIST_VIEW = "/supply/supplyList";
    private static final String SUPPLY_DETAILS_VIEW = "/supply/supplydetails";

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

    @GetMapping("/details/{supplyId}")
    public String supplyDetails(@PathVariable("supplyId") Long supplyId, Model model) {
        Optional<SupplyDetailsDto> supplyOptional = supplyResponseDtoService.getSupplyById(supplyId);
        if (supplyOptional.isPresent()) {
            model.addAttribute("supply", supplyOptional.get());
            return "/supply/supplyDetailsFragment"; // 실제 템플릿 파일명으로 교체하세요.
        } else {
            // 여기에 오류 페이지로 리다이렉트하거나 오류 메시지를 보낼 수 있습니다.
            return "redirect:/supply/list"; // 적절한 리다이렉트 URL로 교체하세요.
        }
    }



    @PostMapping("/delete/{supplyId}")
    public String deleteSupply(@PathVariable Long supplyId) {
        supplyResponseDtoService.deleteSupply(supplyId);
        return "redirect:/supply/list"; // 삭제 후 공급품 목록 페이지로 리다이렉트합니다. 실제 리다이렉트 경로는 상황에 따라 변경될 수 있습니다.
    }
    @GetMapping("/index")
    public String list(Model model) {
        model.addAttribute("categories", LargeCategory.values());
        return "/supply/dashboard";
    }

    @PostMapping("/data/{largeCategory}")
    @ResponseBody
    public Map<String, Long> getCategoryData(@PathVariable LargeCategory largeCategory) {
        List<SupplyResponseDto> supplyDtos = supplyResponseDtoService.getSuppliesAsDtos(String.valueOf(largeCategory));
        return supplyResponseDtoService.getStatusCountsFromDtos(supplyDtos);
    }






}
