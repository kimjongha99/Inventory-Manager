package com.springboot.inventory.supply.repository;

import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findByDeletedFalse();

    List<Supply> findDistinctByStatusAndDeletedFalse(SupplyStatusEnum status);
}
