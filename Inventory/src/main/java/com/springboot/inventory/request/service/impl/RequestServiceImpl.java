package com.springboot.inventory.request.service.impl;

import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.request.dto.RequestDTO;
import com.springboot.inventory.request.repository.RequestRepository;
import com.springboot.inventory.request.service.RequestService;
import com.springboot.inventory.supply.repository.SupplyRepository;
import com.springboot.inventory.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;
    private final SupplyRepository supplyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, CategoryRepository categoryRepository,
                              SupplyRepository supplyRepository, ModelMapper modelMapper) {

        this.requestRepository = requestRepository;
        this.categoryRepository = categoryRepository;
        this.supplyRepository = supplyRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseDTO<RequestDTO> registerRequest(RequestDTO requestDTO, User user) {

        Request request = modelMapper.map(requestDTO, Request.class);
        
        // 요청 종류 불러오기
        RequestTypeEnum requestType = RequestTypeEnum.fromString(requestDTO.getType());
        Category category =
                categoryRepository.findByCategoryName(requestDTO.getCategory()).orElse(null);

        // 추가 매핑
        request.setRequestType(requestType);
        request.setCategory(category);
        request.setUser(user);

        // 저장
        requestRepository.save(request);

        return new ResponseDTO<>(true, null);
    }

    public ResponseDTO<ArrayList<?>> getRequestUnhandled(String type) {

        RequestTypeEnum requestType = RequestTypeEnum.fromString(type);

        ArrayList<?> requestList = requestRepository.findAllByAcceptAndRequestType(null,
                requestType).orElse(new ArrayList<>());

        return new ResponseDTO<>(true, requestList);
    }

    public ResponseDTO<Map<String, Object>> getRentalRequestInfo(String requestId) {

        Request request = requestRepository.findByRequestId(Long.parseLong(requestId)).orElse(null);

        Category category = request.getCategory();

        ArrayList<Supply> supplyList =
                supplyRepository.findAllByCategoryAndStateIsNull(category).orElse(new ArrayList<>());

        Map<String, Object> data = new HashMap<>();

        data.put("requestId", requestId);
        data.put("supplyList", supplyList);

        return new ResponseDTO<>(true, data);
    }

    public ResponseDTO<?> updateSupplyState(String reqId, String supId, String requestType) {

        Long requestId = Long.parseLong(reqId);
        Long supplyId = Long.parseLong(supId);

        RequestTypeEnum state = RequestTypeEnum.fromString(requestType);

        Request request = requestRepository.findByRequestId(requestId).orElse(null);
        Supply supply = supplyRepository.findBySupplyId(supplyId).orElse(null);

        supply.setState(state);
        request.setSupply(supply);
        request.setAccept(true);

        requestRepository.save(request);
        supplyRepository.save(supply);

        return new ResponseDTO<>(true, null);
    }

}
