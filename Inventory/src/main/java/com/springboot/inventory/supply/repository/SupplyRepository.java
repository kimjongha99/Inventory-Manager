package com.springboot.inventory.supply.repository;

import com.springboot.inventory.supply.domain.entity.Category;
import com.springboot.inventory.supply.domain.entity.Supply;
import com.springboot.inventory.supply.domain.entity.User;
import com.springboot.inventory.supply.domain.enums.SupplyStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findByDeletedFalse();

    List<Supply> findDistinctByStatusAndDeletedFalse(SupplyStatusEnum status);
}
