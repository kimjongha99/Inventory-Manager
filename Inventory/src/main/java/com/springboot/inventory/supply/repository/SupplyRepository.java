package com.springboot.inventory.supply.repository;

import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Optional<ArrayList<Supply>> findAllByCategoryAndStateIsNot(Category category,
                                                               RequestTypeEnum requestTypeEnum);

    Optional<Supply> findBySupplyId(Long supplyId);


}
