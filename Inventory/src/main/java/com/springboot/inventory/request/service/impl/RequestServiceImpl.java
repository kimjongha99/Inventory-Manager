package com.springboot.inventory.request.service.impl;

import com.springboot.inventory.common.dto.ResponseDTO;
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

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, UserRepository userRepository,
                              ModelMapper modelMapper) {

        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseDTO<RequestDTO> registerRequest(RequestDTO requestDTO, User user) {

        Request request = modelMapper.map(requestDTO, Request.class);
        
        // 요청 종류 불러오기
        RequestTypeEnum requestType = RequestTypeEnum.fromString(requestDTO.getType());

        // 추가 매핑
        request.setRequestType(requestType);
        request.setUser(user);

        // 저장
        requestRepository.save(request);

        return new ResponseDTO<>(true, null);
    }

    public ResponseDTO<ArrayList<RequestDTO>> getRequestUnhandled() {

        requestRepository.findAllByAcceptAndRequestType(null, RequestTypeEnum.REPAIR);

        return new ResponseDTO<>(true, null);
    }

}
