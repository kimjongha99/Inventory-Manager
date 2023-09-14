package com.springboot.inventory.request.repository;

import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.request.dto.RequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    // 유저 요청 기록
    Optional<RequestDTO> findAllByUser(String email);

    // 미처리 요청 (수리, 대여, 반납, 구매)
    Optional<RequestDTO> findAllByAcceptAndRequestType(Boolean accept, RequestTypeEnum type);





}
