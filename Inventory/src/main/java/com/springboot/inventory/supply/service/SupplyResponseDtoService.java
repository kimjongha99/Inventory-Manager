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
    public List<SupplyResponseDto> findByDeletedFalse() {
        List<Supply> supplies = supplyRepository.findByDeletedFalse();
        return convertToSupplyResponseDtos(supplies);
    }
    @Transactional
    public List<SupplyResponseDto> convertToSupplyResponseDtos(List<Supply> supplies) {
        return supplies.stream()
                .map(supply -> modelMapper.map(supply, SupplyResponseDto.class))
                .collect(Collectors.toList());
    }
    @Transactional
    public List<SupplyResponseDto> findDistinctByStatusAndDeletedFalse(SupplyStatusEnum status){
        List<Supply> supplies = supplyRepository.findDistinctByStatusAndDeletedFalse(status);
        return convertToSupplyResponseDtos(supplies);
    }
    @Transactional
    public Page<SupplyResponseDto> findAllByDeletedFalse(Pageable pageable) {
        Page<Supply> supplies = supplyRepository.findAllByDeletedFalse(pageable);
        return supplies.map(supply -> modelMapper.map(supply, SupplyResponseDto.class));
    }

    @Transactional
    public Page<SupplyResponseDto> findDistinctByStatusAndDeletedFalse(SupplyStatusEnum status, Pageable pageable){
        // 원하는 쿼리를 페이지네이션과 함께 처리하도록 수정하세요. 아래는 예시입니다.
        Page<Supply> supplies = supplyRepository.findDistinctByStatusAndDeletedFalse(status, pageable);
        return supplies.map(supply -> modelMapper.map(supply, SupplyResponseDto.class));
    }
}
