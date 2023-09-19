package com.springboot.inventory.request.controller;

//
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.common.userDetails.CustomUserDetails;
import com.springboot.inventory.request.dto.*;
import com.springboot.inventory.request.service.RequestService;

//
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/request-api")
public class RequestRestController {

    private final RequestService requestService;

    public RequestRestController(RequestService requestService) {
        this.requestService = requestService;
    }

    /* ========================================================================= */
    /* ================================== USER ================================= */
    /* ========================================================================= */

    @PostMapping(value = "/user-rental")
    public ResponseEntity<?> rentalRequest(@RequestBody RentalRequestDTO requestDTO,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();
        requestService.registerRentalRequest(requestDTO, user);

        return ResponseEntity.ok("요청 완료");
    }

    @PostMapping(value = "/user-return")
    public ResponseEntity<?> returnRequest(@RequestBody ReturnRequestDTO returnRequestDTO,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();
        requestService.registerReturnRequest(returnRequestDTO, user);

        return ResponseEntity.ok("요청 완료");
    }

    /* ========================================================================= */
    /* ================================= ADMIN ================================= */
    /* ========================================================================= */


    @GetMapping(value = "/admin-request/count")
    public ResponseEntity<?> getRequestList(@RequestBody String type) {

        RequestTypeEnum requestTypeEnum = RequestTypeEnum.fromString(type);

        ArrayList<?> requestList = requestService.getRequestUnhandled(requestTypeEnum).getData();

        return ResponseEntity.ok(requestList);
    }

    @PostMapping(value = "/admin-request/rental-request-approve")
    public ResponseEntity<?> approveRentalRequest(@RequestBody ApproveDTO approveDTO) {

        requestService.approveRequest(approveDTO, RequestTypeEnum.RENTAL);

        return ResponseEntity.ok("승인되었습니다.");
    }

    @PostMapping(value = "/admin-request/rental-request-reject")
    public ResponseEntity<?> rejectRentalRequest(@RequestBody RentalRejectDTO rentalRejectDTO) {

        requestService.rejectRequest(rentalRejectDTO);
        
        return ResponseEntity.ok("승인 거부되었습니다.");
    }

    @PostMapping(value = "/admin-request/return-request-approve")
    public ResponseEntity<?> approveReturnRequest(@RequestBody ApproveDTO approveDTO) {

        requestService.approveRequest(approveDTO, RequestTypeEnum.RETURN);

        return ResponseEntity.ok("반납 처리 완료");
    }

}
