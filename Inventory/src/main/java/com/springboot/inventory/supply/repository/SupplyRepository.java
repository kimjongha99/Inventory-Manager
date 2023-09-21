package com.springboot.inventory.supply.repository;

//
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.enums.RequestTypeEnum;

//
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//
import java.util.ArrayList;
import java.util.Optional;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    @Query("SELECT s FROM Supply s WHERE s.category = :category AND (s.state IS NULL OR s.state <> :requestType)")
    Optional<ArrayList<Supply>> findAllByCategoryAndStateIsNot(@Param("category") Category category,
                                                               @Param("requestType") RequestTypeEnum requestTypeEnum);

    Optional<Supply> findBySupplyId(Long supplyId);

    Integer countByCategoryAndStateIsNot(Category category, RequestTypeEnum requestTypeEnum);

}
