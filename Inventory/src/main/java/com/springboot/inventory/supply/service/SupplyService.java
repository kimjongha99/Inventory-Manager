package com.springboot.inventory.supply.service;

import com.springboot.inventory.supply.domain.entity.Supply;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyService {
    @Autowired
    private SupplyRepository supplyRepository;

    public List<SupplyResponseDto> getAll() {
        List<Supply> supplies = supplyRepository.findAll();
        return supplies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private SupplyResponseDto convertToDto(Supply supply) {
        SupplyResponseDto dto = new SupplyResponseDto();
        dto.setSupplyId(supply.getSupplyId());
        dto.setSerialNum(supply.getSerialNum());
        dto.setModelContent(supply.getModelContent());
        dto.setAmount(supply.getAmount());
        dto.setModelName(supply.getModelName());
        dto.setDeleted(supply.isDeleted());
        dto.setUser(supply.getUser());
        dto.setCategory(supply.getCategory());
        return dto;
    }
}