package com.springboot.inventory.supply.service;

import com.springboot.inventory.supply.domain.entity.Supply;
import com.springboot.inventory.supply.domain.enums.SupplyStatusEnum;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.repository.SupplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyResponseDtoService {

    private final SupplyRepository supplyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplyResponseDtoService(SupplyRepository supplyRepository, ModelMapper modelMapper) {
        this.supplyRepository = supplyRepository;
        this.modelMapper = modelMapper;
    }

    public List<SupplyResponseDto> findByDeletedFalse() {
        List<Supply> supplies = supplyRepository.findByDeletedFalse();
        return convertToSupplyResponseDtos(supplies);
    }

    public List<SupplyResponseDto> convertToSupplyResponseDtos(List<Supply> supplies) {
        return supplies.stream()
                .map(supply -> modelMapper.map(supply, SupplyResponseDto.class))
                .collect(Collectors.toList());
    }
    public List<SupplyResponseDto> findDistinctByStatusAndDeletedFalse(SupplyStatusEnum status){
        List<Supply> supplies = supplyRepository.findDistinctByStatusAndDeletedFalse(status);
        return convertToSupplyResponseDtos(supplies);
    }


}
