package com.springboot.inventory.supply.service;

import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.repository.SupplyRepository;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.SupplyStatusEnum;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
