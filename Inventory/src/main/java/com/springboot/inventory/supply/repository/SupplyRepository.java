package com.springboot.inventory.supply.repository;

import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Optional<ArrayList<Supply>> findAllByState(RequestTypeEnum requestTypeEnum);

    Optional<ArrayList<Supply>> findAllByCategoryAndStateIsNull(Category category);

    Optional<Supply> findBySupplyId(Long supplyId);


}
