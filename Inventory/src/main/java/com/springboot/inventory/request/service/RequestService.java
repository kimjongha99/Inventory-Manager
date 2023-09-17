package com.springboot.inventory.request.service;

import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.request.dto.RequestDTO;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public interface RequestService {

    ResponseDTO<RequestDTO> registerRequest(RequestDTO requestDTO, User user);

    ResponseDTO<ArrayList<?>> getRequestUnhandled(String type);

    ResponseDTO<Map<String, Object>> getRentalRequestInfo(String requestId);

    ResponseDTO<?> updateSupplyState(String supplyId, String requestId,
                                        String requestType);

}
