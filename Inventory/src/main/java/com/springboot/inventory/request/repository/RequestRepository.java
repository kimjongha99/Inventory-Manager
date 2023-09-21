package com.springboot.inventory.request.repository;

//
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;

//
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestRepository extends JpaRepository<Request, Long> {

    // 유저 요청 기록
    Page<List<Request>> findAllByUser(User user, Pageable pageable);

    // 미승인 요청
    Optional<ArrayList<?>> findAllByAcceptAndRequestType(Boolean accept, RequestTypeEnum type);

    // 미승인 요청(상세 페이지 - 페이징)
    Page<List<Request>> findAllByAcceptAndRequestTypeAndCategory(Boolean accept,
                                                     RequestTypeEnum requestTypeEnum,
                                                     Category category, Pageable pageable);

    Page<List<Request>> findAllByAcceptAndRequestType(Boolean accept, RequestTypeEnum type,
                                                      Pageable pageable);

    Optional<Request> findByRequestId(Long requestId);

    // 대여 상태 품목
    @Query("SELECT r FROM Request r WHERE r.user = :user AND r.requestType = :requestType AND " +
            " r.supply IS NOT NULL AND (r.returnAvailable = true OR r.returnAvailable IS NULL)")
    List<Request> findAllByUserAndRequestTypeAndSupplyIsNotNullAndReturnAvailableIsTrue(@Param(
            "user") User user,
                                                                       @Param("requestType") RequestTypeEnum requestType);



}
