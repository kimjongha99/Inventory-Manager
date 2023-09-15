package com.springboot.inventory.request.service.impl;

import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.request.dto.RequestDTO;
import com.springboot.inventory.request.repository.RequestRepository;
import com.springboot.inventory.request.service.RequestService;
import com.springboot.inventory.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, CategoryRepository categoryRepository,
                              ModelMapper modelMapper) {

        this.requestRepository = requestRepository;
        this.categoryRepository = categoryRepository;
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
}
