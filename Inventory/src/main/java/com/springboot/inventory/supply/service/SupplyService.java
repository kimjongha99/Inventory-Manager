package com.springboot.inventory.supply.service;

import com.springboot.inventory.common.entity.Team;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.supply.dto.SupplyDTO;
import com.springboot.inventory.supply.repository.SupplyRepository;
import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.team.repository.TeamRepository;
import com.springboot.inventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;


    @Transactional
    public Supply createSupply(SupplyDTO supplyDTO) throws Exception {
        // SupplyDTO에서 필요한 정보 추출
        String serialNum = supplyDTO.getSerialNum();
        String modelContent = supplyDTO.getModelContent();
        int amount = supplyDTO.getAmount();
        String modelName = supplyDTO.getModelName();
        SupplyStatusEnum status = supplyDTO.getStatus();
        LargeCategory largeCategory = supplyDTO.getLargeCategory();
        String categoryName = supplyDTO.getCategoryName();
        MultipartFile multipartFile = supplyDTO.getMultipartFile();


        //image 처리
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image"; //경로설정
        UUID uuid = UUID.randomUUID(); // 식별자 랜덤설정
        String imageName = uuid + "_" + multipartFile.getOriginalFilename();
        File saveImage = new File(projectPath, imageName);
        multipartFile.transferTo(saveImage);
        supplyDTO.setImage(imageName);
        supplyDTO.setImagePath("/image/" + imageName);

        String image = supplyDTO.getImage();
        String imagePath = supplyDTO.getImagePath();


        //카테고리
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);

        Category newCategory = null;

        if (category.isPresent()) {
            newCategory = category.get();
        } else {
            // 만약 카테고리가 존재하지 않으면 새로운 카테고리 생성
            newCategory = Category.builder()
                    .largeCategory(supplyDTO.getLargeCategory())
                    .categoryName(supplyDTO.getCategoryName())
                    .deleted(false)
                    .build();
            categoryRepository.save(newCategory);
        }

        //user 사용자 설정
        User user = null;
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(supplyDTO.getUserId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        //team 설정
        Team team = null;
        Optional<Team> teamOptional = teamRepository.findByIdAndDeletedFalse(supplyDTO.getTeamId());
        if (teamOptional.isPresent()) {
            team = teamOptional.get();
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }


        // Supply 엔티티 생성 및 저장
        Supply supply = Supply.builder()
                .serialNum(serialNum)
                .modelContent(modelContent)
                .amount(amount)
                .image(image)
                .imagePath(imagePath)
                .modelName(modelName)
                .deleted(false)
                .status(status)
                .category(newCategory)
                .user(user)
                .team(team)
                .build();

        return supplyRepository.save(supply); // 저장 후 그 supply를 리턴
    }


    @Transactional
    public Supply updateSupply(Long supplyId, SupplyDTO supplyDTO) throws Exception {

        Optional<Supply> supplyOptional = supplyRepository.findById(supplyId);

        if (!supplyOptional.isPresent()) {
            throw new EntityNotFoundException("Supply not found with ID: " + supplyId);
        } //id가 없는경우 예외처리

        //image 처리
        MultipartFile multipartFile = supplyDTO.getMultipartFile();

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image"; //경로설정
        UUID uuid = UUID.randomUUID(); // 식별자 랜덤설정
        String imageName = uuid + "_" + multipartFile.getOriginalFilename();
        File saveImage = new File(projectPath, imageName);
        multipartFile.transferTo(saveImage);
        supplyDTO.setImage(imageName);
        supplyDTO.setImagePath("/image/" + imageName);

        //user 사용자 설정
        User user = null;
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(supplyDTO.getUserId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        //team 설정
        Team team = null;
        Optional<Team> teamOptional = teamRepository.findByIdAndDeletedFalse(supplyDTO.getTeamId());
        if (teamOptional.isPresent()) {
            team = teamOptional.get();
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        Supply supply = supplyOptional.get(); //supply 엔티티 추출

        supply.setSerialNum(supplyDTO.getSerialNum());
        supply.setModelContent(supplyDTO.getModelContent());
        supply.setAmount(supplyDTO.getAmount());
        supply.setImage(supplyDTO.getImage());
        supply.setImagePath(supply.getImagePath());
        supply.setModelName(supplyDTO.getModelName());
        supply.setStatus(supplyDTO.getStatus());
        supply.setUser(user);
        supply.setTeam(team);


        LargeCategory largeCategory = supplyDTO.getLargeCategory();
        String categoryName = supplyDTO.getCategoryName();

        if (largeCategory != null) {
            supply.getCategory().setLargeCategory(largeCategory);
        }
        if (categoryName != null) {
            supply.getCategory().setCategoryName(categoryName);
        }

        return supplyRepository.save(supply);
    }

    @Transactional
    public List<LargeCategory> getLargeCategories() {
        return Arrays.asList(LargeCategory.values()); // 모든 LargeCategory 목록 반환
    }

    @Transactional
    public Map<LargeCategory, List<SupplyDTO>> getSupplyUserByCategory(Long userId) {
        List<Supply> supplyList = supplyRepository.findByUserId(userId);
        Map<LargeCategory, List<SupplyDTO>> supplyByCategoryMap = new HashMap<>();

        // 비품을 largeCategory 별로 분류
        for (Supply supply : supplyList) {
            LargeCategory largeCategory = supply.getCategory().getLargeCategory();
            SupplyDTO supplyDTO = new SupplyDTO();
            supplyDTO.setModelName(supply.getModelName());
            supplyDTO.setStatus(supply.getStatus());
            supplyDTO.setCategoryName(supply.getCategory().getCategoryName());
            supplyDTO.setImagePath(supply.getImagePath());

            // 해당 LargeCategory에 대한 리스트가 이미 있으면 가져오고, 없으면 새로 생성
            supplyByCategoryMap.computeIfAbsent(largeCategory, k -> new ArrayList<>()).add(supplyDTO);
        }
        return supplyByCategoryMap;
    }


    // 비품 재고 현황
    @Transactional
    public Map<LargeCategory, List<SupplyDTO>> getStockList() {
        List<Supply> all = supplyRepository.findAll();
        Map<LargeCategory, List<SupplyDTO>> supplyStockMap = new HashMap<>();

        for (Supply supply : all) {

            if (supply.getStatus() == SupplyStatusEnum.STOCK) {
                LargeCategory largeCategory = supply.getCategory().getLargeCategory();
                SupplyDTO supplyDTO = new SupplyDTO();
                supplyDTO.setModelName(supply.getModelName());
                supplyDTO.setCategoryName(supply.getCategory().getCategoryName());
                supplyDTO.setImagePath(supply.getImagePath());
                supplyDTO.setAmount(supply.getAmount());

                supplyStockMap.computeIfAbsent(largeCategory, k -> new ArrayList<>()).add(supplyDTO);
            }
        }
        return supplyStockMap;

    }
}
