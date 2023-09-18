package com.springboot.inventory.request.service;

import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.request.dto.RentalRejectDTO;
import com.springboot.inventory.request.dto.RequestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RequestService {

    ResponseDTO<RequestDTO> registerRequest(RequestDTO requestDTO, User user);

    ResponseDTO<ArrayList<?>> getRequestUnhandled(String type);

    ResponseDTO<Map<String, Object>> getRentalRequestInfo(String requestId);

    ResponseDTO<?> approveRequest(String supplyId, String requestId,
                                        String requestType);

    ResponseDTO<?> rejectRequest(RentalRejectDTO rentalRejectDTO);

    ResponseDTO<List<Request>> getUserRequestHistory(User user);

}
