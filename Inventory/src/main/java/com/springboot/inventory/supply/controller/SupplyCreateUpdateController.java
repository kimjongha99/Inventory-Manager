package com.springboot.inventory.supply.controller;

import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.Team;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.supply.dto.SupplyDTO;
import com.springboot.inventory.supply.service.SupplyService;
import com.springboot.inventory.team.repository.TeamRepository;
import com.springboot.inventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/supply")
@RequiredArgsConstructor
public class SupplyCreateUpdateController {

    private final SupplyService supplyService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @GetMapping("")
    public  String home() {
        return "home";
    }

    @GetMapping("/create")
    public String createSupplyForm(Model model) {
        SupplyDTO supplyDTO = new SupplyDTO(); // SupplyDTO 객체 생성
        List<User> userList = userRepository.findAll();
        List<Team> teamList = teamRepository.findAll();
        model.addAttribute("userList", userList);
        model.addAttribute("teamList", teamList);
        model.addAttribute("supplyDTO", supplyDTO); // 모델에 supplyDTO 추가
        return "supplyCreate"; // supplyCreate.html 템플릿을 렌더링
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
        List<User> userList = userRepository.findAll();
        List<Team> teamList = teamRepository.findAll();
        model.addAttribute("userList", userList);
        model.addAttribute("teamList", teamList);
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

    @GetMapping("/mysupply/{userId}")
    public String getSupplyUserByCategory(@PathVariable Long userId, Model model) {
        List<LargeCategory> largeCategories = supplyService.getLargeCategories(); // LargeCategory 목록 가져오기
        Map<LargeCategory, List<SupplyDTO>> supplyByCategoryMap = supplyService.getSupplyUserByCategory(userId);
        model.addAttribute("largeCategories", largeCategories);
        model.addAttribute("supplyByCategoryMap", supplyByCategoryMap);
        return "mySupply";
    }

    @GetMapping("/stock")
    public String getSupplyStock(Model model) {
        List<LargeCategory> largeCategories = supplyService.getLargeCategories();
        Map<LargeCategory, List<SupplyDTO>> supplyStockMap = supplyService.getStockList();
        model.addAttribute("largeCategories", largeCategories);
        model.addAttribute("supplyByCategoryMap", supplyStockMap);
        return "stock";
    }

}

