package com.springboot.inventory.supply.service;

import com.springboot.inventory.supply.domain.entity.Supply;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyResponseDtoService {

    private SupplyRepository supplyRepository;

    @Autowired
    public SupplyResponseDtoService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    public List<SupplyResponseDto> getAllSupplyResponseDtos() {
        List<Supply> supplies = supplyRepository.findAll();

        return supplies.stream()
                .map(SupplyResponseDto::new)
                .collect(Collectors.toList());
    }

}
