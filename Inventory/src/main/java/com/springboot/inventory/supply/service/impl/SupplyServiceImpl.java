package com.springboot.inventory.supply.service.impl;

import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.userDetails.CustomUserDetails;
import com.springboot.inventory.supply.dto.SupplyRegisterDTO;
import com.springboot.inventory.supply.repository.SupplyRepository;
import com.springboot.inventory.supply.service.SupplyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplyServiceImpl implements SupplyService {

    private final SupplyRepository supplyRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplyServiceImpl(SupplyRepository supplyRepository ,
                             CategoryRepository categoryRepository, ModelMapper modelMapper) {

        this.supplyRepository = supplyRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;

    }

    public ResponseDTO<?> registerSupply(SupplyRegisterDTO supplyRegisterDTO,
                                         CustomUserDetails userDetails) {

        Category category =
                categoryRepository.findByCategoryName(supplyRegisterDTO.getCategory()).orElse(null);

        Supply supply = modelMapper.map(supplyRegisterDTO, Supply.class);

        supply.setUser(userDetails.getUser());
        supply.setCategory(category);

        supplyRepository.save(supply);

        return new ResponseDTO<>(true, null);
    }

}
