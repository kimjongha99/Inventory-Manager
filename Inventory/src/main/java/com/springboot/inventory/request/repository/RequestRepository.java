package com.springboot.inventory.request.repository;

import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

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
    List<Request> findByUserAndRequestTypeAndReturnAvailableIsTrue(User user,
                                                               RequestTypeEnum requestTypeEnum);
}
