package com.springboot.inventory.supply.repository;

import com.springboot.inventory.supply.domain.entity.Supply;
import com.springboot.inventory.supply.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

}
