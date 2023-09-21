package com.springboot.inventory.request.repository;

//
import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;

//
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    // 유저 요청 기록
    List<Request> findAllByUser(User user);

    // 미처리 요청 (수리, 대여, 반납, 구매)
    Optional<ArrayList<?>> findAllByAcceptAndRequestType(Boolean accept, RequestTypeEnum type);

    Optional<Request> findByRequestId(Long requestId);

    // 대여 상태 품목
    @Query("SELECT r FROM Request r WHERE r.user = :user AND r.requestType = :requestType AND " +
            " r.supply IS NOT NULL AND (r.returnAvailable = true OR r.returnAvailable IS NULL)")
    List<Request> findAllByUserAndRequestTypeAndSupplyIsNotNullAndReturnAvailableIsTrue(@Param(
            "user") User user,
                                                                       @Param("requestType") RequestTypeEnum requestType);



}
