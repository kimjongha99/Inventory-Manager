package com.springboot.inventory.supply.service;

import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.supply.dto.SupplyDetailsDto;
import com.springboot.inventory.supply.dto.SupplyDto;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplyResponseDtoService {

    private final SupplyRepository supplyRepository;

    @Autowired
    public SupplyResponseDtoService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    @Transactional
    public Page<SupplyResponseDto> findAllByDeletedFalse(Pageable pageable) {
        Page<Supply> supplies = supplyRepository.findAllByDeletedFalse(pageable);
        return convertToDtoPage(supplies);
    }
    public List<SupplyResponseDto> findAllByDeletedFalse() {
        List<Supply> supplies = supplyRepository.findAllByDeletedFalse();
        return convertToDtoPage(supplies);
    }
    @Transactional
    public Page<SupplyResponseDto> findDistinctByStatusAndDeletedFalse(SupplyStatusEnum status, Pageable pageable) {
        Page<Supply> supplies = supplyRepository.findDistinctByStatusAndDeletedFalse(status, pageable);
        return convertToDtoPage(supplies);
    }

    @Transactional
    public Page<SupplyResponseDto> searchByKeyword(String keyword, Pageable pageable) {
        Page<Supply> supplies = supplyRepository.searchByKeyword(keyword, pageable);
        return convertToDtoPage(supplies);
    }

    @Transactional
    public Optional<SupplyDetailsDto> getSupplyById(Long supplyId) {
        return supplyRepository.findSupplyWithCategoryNameById(supplyId).map(SupplyDetailsDto::fromSupplyDetails);
    }

    @Transactional
    public Page<SupplyResponseDto> searchByKeywordAndStatus(String keyword, SupplyStatusEnum status, Pageable pageable) {
        Page<Supply> supplies = supplyRepository.searchByKeywordAndStatus(keyword, status, pageable);
        return convertToDtoPage(supplies);
    }

    @Transactional
    public void deleteSupply(Long id) {
        supplyRepository.deleteById(id);
    }

    private Page<SupplyResponseDto> convertToDtoPage(Page<Supply> supplies) {
        return supplies.map(SupplyResponseDto::fromSupply);
    }

    private List<SupplyResponseDto> convertToDtoPage(List<Supply> supplies) {
        return supplies.stream()
                .map(SupplyResponseDto::fromSupply)
                .collect(Collectors.toList());
    }
    public List<SupplyResponseDto> getSuppliesAsDtos(String categoryName) {
        List<Supply> supplies = supplyRepository.findAllByCategoryNameAndDeletedFalse(categoryName);
        return convertToDtoPage(supplies);
    }

    public Map<String, Long> getStatusCountsFromDtos(List<SupplyResponseDto> supplyDtos) {
        return supplyDtos.stream()
                .collect(Collectors.groupingBy(SupplyResponseDto::getStatus, Collectors.counting()));
    }



}
