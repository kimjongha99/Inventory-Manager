package com.springboot.inventory.supply.controller;

import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.category.service.CategoryService;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.supply.dto.SupplyDto;
import com.springboot.inventory.supply.service.SupplyService;
import com.springboot.inventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/supply")
@RequiredArgsConstructor
public class SupplyCreateUpdateController {

    private final SupplyService supplyService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @GetMapping("")
    public  String home() {
        return "home";
    }

    //대분류 소분류 선택
    @GetMapping("/getSubcategories")
    @ResponseBody
    public List<String> getSubcategoriesByLargeCategory(LargeCategory largeCategory) {
        // 대분류에 해당하는 카테고리 리스트 가져오기
        List<Category> categories = categoryService.findByLargeCategory(largeCategory);
        // 카테고리에서 소분류 값만 추출하여 리스트로 만들기
        List<String> subcategories = new ArrayList<>();
        for (Category category : categories) {
            subcategories.add(category.getCategoryName()); // 또는 원하는 속성으로 변경
        }
        return subcategories;
    }

    @GetMapping("/create")
    public String createSupplyForm(Model model) {
        SupplyDto supplyDto = new SupplyDto(); // SupplyDTO 객체 생성
        List<User> userList =  userRepository.findByRolesAndDeleted(UserRoleEnum.USER, false);
        model.addAttribute("userList", userList);
        model.addAttribute("supplyDto", supplyDto); // 모델에 supplyDto 추가
        return "/supply/supplyCreate"; // supplyCreate.html 템플릿을 렌더링
    }

    @PostMapping("/create")
    public String createSupply(
            @ModelAttribute @Valid SupplyDto supplyDto) throws Exception {
        supplyService.createSupply(supplyDto);
        return "redirect:/supply/list";
    }

    @GetMapping("/mysupply/{userId}")
    public String getSupplyUserByCategory(@PathVariable Long userId, Model model) {
        List<LargeCategory> largeCategories = supplyService.getLargeCategories(); // LargeCategory 목록 가져오기
        Map<LargeCategory, List<SupplyDto>> supplyByCategoryMap = supplyService.getSupplyUserByCategory(userId);
        model.addAttribute("largeCategories", largeCategories);
        model.addAttribute("supplyByCategoryMap", supplyByCategoryMap);
        return "/supply/mySupply";
    }

    @GetMapping("/stock")
    public String getSupplyStock(Model model) {
        List<LargeCategory> largeCategories = supplyService.getLargeCategories();
        Map<LargeCategory, List<SupplyDto>> supplyStockMap = supplyService.getStockList();
        model.addAttribute("largeCategories", largeCategories);
        model.addAttribute("supplyByCategoryMap", supplyStockMap);
        return "/supply/stock";
    }

    @GetMapping("/update/{supplyId}")
    public String updateSupplyForm(@PathVariable Long supplyId, Model model) {
        // Supply 데이터 조회
        Supply supply = supplyService.getSupply(supplyId); // supplyService에서 해당 메서드를 구현해야 합니다.
        Long userId = (supply.getUser() != null) ? supply.getUser().getUserId() : null;

        // 폼에 사용될 SupplyDto를 생성하고 Supply 데이터로 초기화
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setLargeCategory(supply.getCategory().getLargeCategory());
        supplyDto.setCategoryName(supply.getCategory().getCategoryName());
        supplyDto.setSerialNum(supply.getSerialNum());
        supplyDto.setModelName(supply.getModelName());
        supplyDto.setModelContent(supply.getModelContent());
        supplyDto.setUserId(userId);
        supplyDto.setStatus(supply.getStatus());

        // 사용자 목록 조회 (사용자 선택 필드를 위해)
        List<User> userList = userRepository.findByRoles(UserRoleEnum.USER); // userService에서 해당 메서드를 구현해야 합니다.

        // 모델에 데이터를 추가하여 Thymeleaf에서 사용할 수 있도록 함
        model.addAttribute("supplyDto", supplyDto);
        model.addAttribute("userList", userList);

        return "/supply/supplyUpdate"; // 수정 폼 템플릿의 이름으로 수정해야 합니다.
    }

    @PostMapping("/update/{supplyId}")
    public String updateSupply(@ModelAttribute("supplyDto") SupplyDto supplyDto, @PathVariable Long supplyId) throws Exception {

         supplyService.updateSupply(supplyId, supplyDto);

        return "redirect:/supply/list" ;
    }

    @GetMapping("/checkDuplicateSerialNumber")
    @ResponseBody
    public boolean checkDuplicateSerialNumber(String serialNumber) {
        boolean isDuplicate = supplyService.isSerialNumberDuplicate(serialNumber);
        return isDuplicate;
    }

}

