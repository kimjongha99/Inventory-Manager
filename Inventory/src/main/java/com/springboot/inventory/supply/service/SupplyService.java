package com.springboot.inventory.supply.service;

import com.springboot.inventory.supply.dto.SupplyDTO;
import com.springboot.inventory.supply.repository.SupplyRepository;
import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public SupplyService(
            SupplyRepository supplyRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository
    ) {
        this.supplyRepository = supplyRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Supply createSupply(SupplyDTO supplyDTO) throws Exception {
        // SupplyDTO에서 필요한 정보 추출
        String serialNum = supplyDTO.getSerialNum();
        String modelContent = supplyDTO.getModelContent();
        int amount = supplyDTO.getAmount();
        //String image = supplyDTO.getImage();
        String modelName = supplyDTO.getModelName();
        SupplyStatusEnum status = supplyDTO.getStatus();
//        Long userId = supplyDTO.getUserId();
        LargeCategory largeCategory = supplyDTO.getLargeCategory();
        String categoryName = supplyDTO.getCategoryName();
        MultipartFile multipartFile = supplyDTO.getMultipartFile();

        // 사용자 및 카테고리 정보 가져오기
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        //image 처리
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image"; //경로설정
        UUID uuid = UUID.randomUUID(); // 식별자 랜덤설정
        String imageName = uuid + "_" + multipartFile.getOriginalFilename();
        File saveImage = new File(projectPath, imageName);
        multipartFile.transferTo(saveImage);
        supplyDTO.setImage(imageName);
        supplyDTO.setImagePath("/image/" +imageName);

        String image = supplyDTO.getImage();
        String imagePath = supplyDTO.getImagePath();


        //카테고리
        Optional<Category> category = categoryRepository.findByCategoryNameAndDeletedFalse(categoryName);

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
        supplyDTO.setImagePath("/image/" +imageName);

        Supply supply = supplyOptional.get(); //supply 엔티티 추출

        supply.setSerialNum(supplyDTO.getSerialNum());
        supply.setModelContent(supplyDTO.getModelContent());
        supply.setAmount(supplyDTO.getAmount());
        supply.setImage(supplyDTO.getImage());
        supply.setImagePath(supply.getImagePath());
        supply.setModelName(supplyDTO.getModelName());
        supply.setStatus(supplyDTO.getStatus());

        LargeCategory largeCategory = supplyDTO.getLargeCategory();
        String categoryName = supplyDTO.getCategoryName();

        if (largeCategory != null) {
            supply.getCategory().setLargeCategory(largeCategory);
        }
        if (categoryName != null) {
            supply.getCategory().setCategoryName(categoryName);
        }

       return  supplyRepository.save(supply);
    }

}


