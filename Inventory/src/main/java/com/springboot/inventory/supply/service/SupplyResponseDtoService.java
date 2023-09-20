package com.springboot.inventory.supply.service;

import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.supply.dto.SupplyDetailsDto;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import com.springboot.inventory.supply.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
    public Page<SupplyResponseDto> findDistinctByStatusAndDeletedFalse(SupplyStatusEnum status, Pageable pageable){
        Page<Supply> supplies = supplyRepository.findDistinctByStatusAndDeletedFalse(status, pageable);
        return convertToDtoPage(supplies);
    }

    // 중복되는 Entity에서 DTO로의 변환 로직을 이 메서드로 분리
    private Page<SupplyResponseDto> convertToDtoPage(Page<Supply> supplies) {
        return supplies.map(SupplyResponseDto::fromSupply);
    }

    @Transactional
    public Page<SupplyResponseDto> searchByKeyword(String keyword, Pageable pageable) {
        Page<Supply> supplies = supplyRepository.searchByKeyword(keyword, pageable);
        System.out.println("Search results: " + supplies.getContent());
        return convertToDtoPage(supplies);
    }

    // Supply ID를 사용하여 특정 Supply 엔티티 조회
    @Transactional
    public Optional<SupplyDetailsDto> getSupplyById(Long supplyId) {
        return supplyRepository.findById(supplyId)
                .map(SupplyDetailsDto::fromSupplyDetails);
    }
    @Transactional
    public Page<SupplyResponseDto> searchByKeywordAndStatus(String keyword, SupplyStatusEnum status, Pageable pageable) {
        Page<Supply> supplies = supplyRepository.searchByKeywordAndStatus(keyword, status, pageable);
        return convertToDtoPage(supplies);
    }

    public void deleteSupply(Long id) {
        // Soft Delete: deleted 필드를 true로 설정
        Supply supply = supplyRepository.findById(id).orElse(null);
        if (supply != null) {
            supply.setDeleted(true);
            supplyRepository.save(supply);
        }
    }
}