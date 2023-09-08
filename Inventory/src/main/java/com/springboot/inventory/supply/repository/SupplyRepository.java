package com.springboot.inventory.supply.repository;

import com.springboot.inventory.common.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

}
