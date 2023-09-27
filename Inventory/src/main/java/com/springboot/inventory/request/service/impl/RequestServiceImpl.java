package com.springboot.inventory.request.service.impl;

import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.request.dto.ApproveDTO;
import com.springboot.inventory.request.dto.RentalRejectDTO;
import com.springboot.inventory.request.dto.RentalRequestDTO;
import com.springboot.inventory.request.dto.ReturnRequestDTO;
import com.springboot.inventory.request.repository.RequestRepository;
import com.springboot.inventory.request.service.RequestService;
import com.springboot.inventory.supply.repository.SupplyRepository;

//
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

//
import java.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

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

    /* ========================================================================= */
    /* ================================== USER ================================= */
    /* ========================================================================= */


    @Transactional
    public ResponseDTO<?> registerRentalRequest(RentalRequestDTO rentalRequestDTO,
                                                          User user) {

        Request request = modelMapper.map(rentalRequestDTO, Request.class);


        RequestTypeEnum requestType = RequestTypeEnum.fromString(rentalRequestDTO.getType());
        Category category =
                categoryRepository.findByCategoryName(rentalRequestDTO.getCategory()).orElse(null);

        request.setRequestType(requestType);
        request.setCategory(category);
        request.setUser(user);

        requestRepository.save(request);

        return new ResponseDTO<>(true, null);
    }


    @Transactional
    public ResponseDTO<?> registerReturnRequest(ReturnRequestDTO returnRequestDTO, User user) {

        Request returnRequest = new Request();
        Request rentalRequest =
                requestRepository.findByRequestId(Long.parseLong(returnRequestDTO.getRentalRequestId())).orElse(null);


        RequestTypeEnum requestType = RequestTypeEnum.fromString(returnRequestDTO.getType());
        Supply supply =
                supplyRepository.findBySupplyId(Long.parseLong(returnRequestDTO.getSupplyId())).orElse(null);
        Category category =
                categoryRepository.findByCategoryName(returnRequestDTO.getCategory()).orElse(null);


        returnRequest.setRequestType(requestType);
        returnRequest.setCategory(category);
        returnRequest.setSupply(supply);
        returnRequest.setRequest(rentalRequest);
        returnRequest.setUser(user);
        rentalRequest.setReturnAvailable(true);


        requestRepository.save(rentalRequest);
        requestRepository.save(returnRequest);

        return new ResponseDTO<>(true, null);
    }


    public ResponseDTO<Page<?>> getUserRequestHistory(User user, int page) {

        Pageable pageable = PageRequest.of(page, 10);

        Page<List<Request>> requestHistory = requestRepository.findAllByUser(user, pageable);

        return new ResponseDTO<>(true, requestHistory);
    }


    public ResponseDTO<List<Request>> getRentalSupplyByUser(User user) {

        List<Request> rentalRequestList =
                requestRepository.findAllByUserAndRequestTypeAndSupplyIsNotNullAndReturnAvailableIsTrue(user,
                        RequestTypeEnum.RENTAL);

        return new ResponseDTO<>(true, rentalRequestList);
    }

    /* ========================================================================= */
    /* ================================= ADMIN ================================= */
    /* ========================================================================= */


    public ResponseDTO<ArrayList<?>> getRequestUnhandled(RequestTypeEnum type) {

        ArrayList<?> requestList = requestRepository.findAllByAcceptAndRequestType(null,
                type).orElse(new ArrayList<>());


        return new ResponseDTO<>(true, requestList);
    }


    public ResponseDTO<Map<String, Object>> getRentalRequestInfo(String requestId) {

        Request request = requestRepository.findByRequestId(Long.parseLong(requestId)).orElse(null);

        Category category = request.getCategory();

        ArrayList<Supply> supplyList =
                supplyRepository.findAllByCategoryAndStatus(category, SupplyStatusEnum.STOCK);

        Map<String, Object> data = new HashMap<>();

        data.put("requestId", requestId);
        data.put("supplyList", supplyList);

        return new ResponseDTO<>(true, data);
    }


    public ResponseDTO<Page<?>> getRequestUnhandledByCategory(RequestTypeEnum type, String categoryName,
                                                              int page) {
        Pageable pageable = PageRequest.of(page, 15);

        Category category = categoryRepository.findByCategoryName(categoryName).orElse(null);

        Page<List<Request>> rentalList;

        if(category != null) {
            rentalList = requestRepository.findAllByAcceptAndRequestTypeAndCategory(null, type, category,
                        pageable);
        } else {
            rentalList = requestRepository.findAllByAcceptAndRequestType(null, type, pageable);
        }

        return new ResponseDTO<>(true, rentalList);
    }


    @Transactional
    public ResponseDTO<?> approveRequest(ApproveDTO approveDTO, RequestTypeEnum requestTypeEnum) {

        Long requestId = Long.parseLong(approveDTO.getRequestId());
        Long supplyId = Long.parseLong(approveDTO.getSupplyId());

        Request request = requestRepository.findByRequestId(requestId).orElse(null);
        Supply supply = supplyRepository.findBySupplyId(supplyId).orElse(null);

        User user = request.getUser();


        request.setSupply(supply);
        request.setAccept(true);
        supply.setUser(user);
        supply.setState(requestTypeEnum);
        supply.setStatus(SupplyStatusEnum.USING);

        if (requestTypeEnum == RequestTypeEnum.RETURN) {
            Request pastRequest =  request.getRequest();

            supply.setUser(null);
            supply.setStatus(SupplyStatusEnum.STOCK);

            pastRequest.setReturnAvailable(false);

            requestRepository.save(pastRequest);
        }

        requestRepository.save(request);

        supplyRepository.save(supply);

        return new ResponseDTO<>(true, null);
    }


    @Transactional
    public ResponseDTO<?> rejectRequest(RentalRejectDTO rentalRejectDTO) {

        String reqId = rentalRejectDTO.getRequestId();
        String comment  = rentalRejectDTO.getComment();

        Long requestId = Long.parseLong(reqId);

        Request request = requestRepository.findByRequestId(requestId).orElse(null);

        request.setAccept(false);
        request.setComment(comment);

        requestRepository.save(request);

        return new ResponseDTO<>(true, null);
    }


}
