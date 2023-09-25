package com.springboot.inventory.supply.service;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.supply.dto.SupplyDto;
import com.springboot.inventory.supply.repository.SupplyRepository;
import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    @Transactional
    public Supply createSupply(SupplyDto supplyDto) throws Exception {
        // SupplyDTO에서 필요한 정보 추출
        String serialNum = supplyDto.getSerialNum();
        String modelContent = supplyDto.getModelContent();
        int amount = supplyDto.getAmount();
        String modelName = supplyDto.getModelName();
        SupplyStatusEnum status = supplyDto.getStatus();
        LargeCategory largeCategory = supplyDto.getLargeCategory();
        MultipartFile multipartFile = supplyDto.getMultipartFile();


        //image 입력이 될때만 저장

        String image = null;
        String imagePath = null;
        if (!multipartFile.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image"; //경로설정
            UUID uuid = UUID.randomUUID(); // 식별자 랜덤설정
            String imageName = uuid + "_" + multipartFile.getOriginalFilename();
            File saveImage = new File(projectPath, imageName);
            multipartFile.transferTo(saveImage);
            supplyDto.setImage(imageName);
            supplyDto.setImagePath("/image/" + imageName);

            image = supplyDto.getImage();
            imagePath = supplyDto.getImagePath();
        }
        //카테고리
        String categoryName = supplyDto.getCategoryName();
        if (categoryName.equals("직접입력")) {
            categoryName = supplyDto.getDirectCategoryName();
        }

        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
        Category newCategory = null;

        if (category.isPresent()) {
            newCategory = category.get();
        } else {
            // 만약 카테고리가 존재하지 않으면 새로운 카테고리 생성
            newCategory = Category.builder()
                    .largeCategory(supplyDto.getLargeCategory())
                    .categoryName(categoryName)
                    .deleted(false)
                    .build();
            categoryRepository.save(newCategory);
        }

        //user 사용자 설정
        User user = null;

        Optional<User> userOptional = userRepository.findByUserId(supplyDto.getUserId());
        System.out.println("supplyDto.getUserId()= " + supplyDto.getUserId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }


        // Supply 엔티티 생성 및 저장
        Supply supply = Supply.builder()
                .serialNum(serialNum)
                .modelContent(modelContent)
                .amount(amount)
                .modelName(modelName)
                .image(image)
                .imagePath(imagePath)
                .deleted(false)
                .status(status)
                .category(newCategory)
                .user(user)
                .build();

        return supplyRepository.save(supply); // 저장 후 그 supply를 리턴
    }

    // 비품 업데이트를 위해 supply 가져오기
    public Supply getSupply(Long supplyId) {
        return supplyRepository.findById(supplyId).orElseThrow();
    }

    @Transactional
    public Supply updateSupply(Long supplyId, SupplyDto supplyDto) throws Exception {

        Supply supply = getSupply(supplyId);

        if (!supplyDto.getSerialNum().equals("")) {
            supply.setSerialNum(supplyDto.getSerialNum());
        }

        if (!supplyDto.getModelName().equals("")) {
            supply.setModelName(supplyDto.getModelName());
        }

        if (!supplyDto.getModelContent().equals("")) {
            supply.setModelContent(supplyDto.getModelContent());
        }

        if (supplyDto.getStatus() != null) {
            supply.setStatus(supplyDto.getStatus());
        }

        //image 처리
        if (!supplyDto.getMultipartFile().isEmpty()) {
            MultipartFile multipartFile = supplyDto.getMultipartFile();

            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image"; //경로설정
            UUID uuid = UUID.randomUUID(); // 식별자 랜덤설정
            String imageName = uuid + "_" + multipartFile.getOriginalFilename();
            File saveImage = new File(projectPath, imageName);
            multipartFile.transferTo(saveImage);
            supplyDto.setImage(imageName);
            supplyDto.setImagePath("/image/" + imageName);

            supply.setImage(supplyDto.getImage());
            supply.setImagePath(supplyDto.getImagePath());
        }
        //user 사용자 설정
        if (supplyDto.getUserId() != null) {
            User user = userRepository.findByUserId(supplyDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
            supply.setUser(user);
        } else {
            supply.setUser(null);
        }
        //카테고리 업데이트 (소분류 중복문제 발생)
        LargeCategory largeCategory = supplyDto.getLargeCategory();
        String categoryName = supplyDto.getCategoryName();

        if (largeCategory == null) {
            largeCategory = supply.getCategory().getLargeCategory();
        }
        if (categoryName.equals("")) {
            categoryName = supply.getCategory().getCategoryName();
        }

        if (categoryName.equals("직접입력")) {
            categoryName = supplyDto.getDirectCategoryName();
        }
        Category existingCategory = categoryRepository.findByLargeCategoryAndCategoryName(largeCategory, categoryName);

        if (existingCategory == null) {
            // 기존 카테고리가 없으면 새 카테고리 생성
            Category newCategory = new Category();
            newCategory.setLargeCategory(largeCategory);
            newCategory.setCategoryName(categoryName);
            // 새 카테고리를 supply에 설정하기전 db에 저장
            categoryRepository.save(newCategory);
            supply.setCategory(newCategory);
        } else {
            // 기존 카테고리가 있으면 supply에 설정
            supply.setCategory(existingCategory);
        }

        supplyRepository.save(supply);
        return supply;
    }

    @Transactional
    public List<LargeCategory> getLargeCategories() {
        return Arrays.asList(LargeCategory.values()); // 모든 LargeCategory 목록 반환
    }

    @Transactional
    public Map<LargeCategory, List<SupplyDto>> getSupplyUserByCategory(Long userId) {

        User user = userRepository.findByUserId(userId).orElse(null);

        List<Supply> supplyList = supplyRepository.findByUser(user);
        Map<LargeCategory, List<SupplyDto>> supplyByCategoryMap = new HashMap<>();

        // 비품을 largeCategory 별로 분류
        for (Supply supply : supplyList) {
            LargeCategory largeCategory = supply.getCategory().getLargeCategory();
            SupplyDto supplyDto = new SupplyDto();
            supplyDto.setModelName(supply.getModelName());
            supplyDto.setStatus(supply.getStatus());
            supplyDto.setCategoryName(supply.getCategory().getCategoryName());
            supplyDto.setImagePath(supply.getImagePath());

            // 해당 LargeCategory에 대한 리스트가 이미 있으면 가져오고, 없으면 새로 생성
            supplyByCategoryMap.computeIfAbsent(largeCategory, k -> new ArrayList<>()).add(supplyDto);
        }
        return supplyByCategoryMap;
    }


    // 비품 재고 현황
    @Transactional
    public Map<LargeCategory, List<SupplyDto>> getStockList() {
        List<Supply> all = supplyRepository.findByStatusAndDeletedFalse(SupplyStatusEnum.STOCK);
        Map<LargeCategory, List<SupplyDto>> supplyStockMap = new HashMap<>();

        for (Supply supply : all) {

            if (supply.getStatus() == SupplyStatusEnum.STOCK ) {
                LargeCategory largeCategory = supply.getCategory().getLargeCategory();
                SupplyDto supplyDto = new SupplyDto();
                supplyDto.setModelName(supply.getModelName());
                supplyDto.setCategoryName(supply.getCategory().getCategoryName());
                supplyDto.setImagePath(supply.getImagePath());

                supplyStockMap.computeIfAbsent(largeCategory, k -> new ArrayList<>()).add(supplyDto);
            }
        }
        return supplyStockMap;

    }

    public boolean isSerialNumberDuplicate(String serialNumber) {
        return supplyRepository.existsBySerialNum(serialNumber);
    }
}
