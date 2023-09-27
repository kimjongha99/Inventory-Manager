package com.springboot.inventory.supply.repository;

import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.supply.dto.SupplyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    Page<Supply> findAllByDeletedFalse(Pageable pageable);
    List<Supply> findAllByDeletedFalse();

    @Query("SELECT s " +
            "FROM Supply s " +
            "LEFT JOIN FETCH s.category c " +
            "WHERE s.supplyId = :supplyId")
    Optional<Supply> findSupplyWithCategoryNameById(@Param("supplyId") Long supplyId);

    Page<Supply> findDistinctByStatusAndDeletedFalse(@Param("status") SupplyStatusEnum status, Pageable pageable);
    @Query("SELECT s FROM Supply s LEFT JOIN s.category c WHERE c.categoryName = :categoryName AND s.deleted = false")
    List<Supply> findAllByCategoryNameAndDeletedFalse(@Param("categoryName") String categoryName);
    @Query("SELECT s FROM Supply s " +
            "LEFT JOIN s.category c " +
            "LEFT JOIN s.user u " +
            "WHERE " +
            "(c.categoryName LIKE %:keyword% OR " +
            "s.modelName LIKE %:keyword% OR " +
            "s.serialNum LIKE %:keyword% OR " +
            "u.username LIKE %:keyword%) AND " +
            "s.deleted = false")
    Page<Supply> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    //keyword 와 status를 모두 고려한 로직
    @Query("SELECT s FROM Supply s " +
            "LEFT JOIN s.category c " +
            "LEFT JOIN s.user u " +
            "WHERE " +
            "(c.categoryName LIKE %:keyword% OR " +
            "s.modelName LIKE %:keyword% OR " +
            "s.serialNum LIKE %:keyword% OR " +
            "u.username LIKE %:keyword%) AND " +
            "s.status = :status AND " +
            "s.deleted = false")
    Page<Supply> searchByKeywordAndStatus(@Param("keyword") String keyword, @Param("status") SupplyStatusEnum status, Pageable pageable);

    List<Supply> findByUser(User user);

    /* ====================================Request========================================*/

    @Query("SELECT s FROM Supply s WHERE s.category = :category AND s.status = :supplyStatusEnum")
    ArrayList<Supply> findAllByCategoryAndStatus(@Param("category")Category category, @Param(
            "supplyStatusEnum") SupplyStatusEnum supplyStatusEnum);

    Optional<Supply> findBySupplyId(Long supId);

    boolean existsBySerialNum(String serialNumber);

    List<Supply> findByStatusAndDeletedFalse(SupplyStatusEnum status) ;
}



