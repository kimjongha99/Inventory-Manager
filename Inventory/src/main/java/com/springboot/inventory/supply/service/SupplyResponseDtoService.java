package com.springboot.inventory.supply.service;

import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.repository.SupplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Page<SupplyResponseDto> findAllByDeletedFalse(Pageable pageable) {
        Page<Supply> supplies = supplyRepository.findAllByDeletedFalse(pageable);
        return convertToDtoPage(supplies);
    }

    @Transactional
    public Page<SupplyResponseDto> findDistinctByStatusAndDeletedFalse(SupplyStatusEnum status, Pageable pageable){
        Page<Supply> supplies = supplyRepository.findDistinctByStatusAndDeletedFalse(status, pageable);
        return convertToDtoPage(supplies);
    }


    // 중복되는 Entity에서 DTO로의 변환 로직을 이 메서드로 분리
    private Page<SupplyResponseDto> convertToDtoPage(Page<Supply> supplies) {
        return supplies.map(supply -> modelMapper.map(supply, SupplyResponseDto.class));
    }

    @Transactional
    public Page<SupplyResponseDto> searchByKeyword(String keyword, Pageable pageable) {
        Page<Supply> supplies = supplyRepository.searchByKeyword(keyword, pageable);
        System.out.println("Search results: " + supplies.getContent());
        return convertToDtoPage(supplies);
    }
}
