package com.springboot.inventory.request.service;

import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.request.dto.ApproveDTO;
import com.springboot.inventory.request.dto.RentalRejectDTO;
import com.springboot.inventory.request.dto.RentalRequestDTO;
import com.springboot.inventory.request.dto.ReturnRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RequestService {

    ResponseDTO<?> registerRentalRequest(RentalRequestDTO rentalRequestDTO, User user);

    ResponseDTO<?> registerReturnRequest(ReturnRequestDTO returnRequestDTO, User user);

    ResponseDTO<ArrayList<?>> getRequestUnhandled(RequestTypeEnum type);

    ResponseDTO<Page<?>> getRequestUnhandledByCategory(RequestTypeEnum type, String categoryName,
                                                       int page);

    ResponseDTO<Map<String, Object>> getRentalRequestInfo(String requestId);

    ResponseDTO<?> approveRequest(ApproveDTO approveDTO,
                                  RequestTypeEnum requestTypeEnum);

    ResponseDTO<?> rejectRequest(RentalRejectDTO rentalRejectDTO);

    ResponseDTO<Page<?>> getUserRequestHistory(User user, int page);

    ResponseDTO<List<Request>> getRentalSupplyByUser(User user);

}
