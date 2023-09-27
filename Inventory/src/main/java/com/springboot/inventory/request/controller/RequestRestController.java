package com.springboot.inventory.request.controller;

//
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.common.security.UserDetailsImpl;
import com.springboot.inventory.request.dto.*;
import com.springboot.inventory.request.service.RequestService;

//
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping(value = "/user-rental", produces = "application/text; charset=utf8")
    public ResponseEntity<?> rentalRequest(@RequestBody RentalRequestDTO requestDTO,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        requestService.registerRentalRequest(requestDTO, user);

        return ResponseEntity.ok("대여 신청 완료");
    }

    @PostMapping(value = "/user-return", produces = "application/text; charset=utf8")
    public ResponseEntity<?> returnRequest(@RequestBody ReturnRequestDTO returnRequestDTO,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        requestService.registerReturnRequest(returnRequestDTO, user);

        return ResponseEntity.ok("요청 완료");
    }

    /* ========================================================================= */
    /* ================================= ADMIN ================================= */
    /* ========================================================================= */


    @GetMapping(value = "/admin-requestinfo")
    public ResponseEntity<?> requestListCount() {

        Map<String, String> requestCount = new HashMap<>();

        int rentalCount =
                requestService.getRequestUnhandled(RequestTypeEnum.RENTAL).getData().size();

        int returnCount =
                requestService.getRequestUnhandled(RequestTypeEnum.RETURN).getData().size();

        requestCount.put("rentalCount", Integer.toString(rentalCount));
        requestCount.put("returnCount", Integer.toString(returnCount));


        return ResponseEntity.ok(requestCount);
    }

    @GetMapping(value = "/admin-request/count")
    public ResponseEntity<?> getRequestList(@RequestBody String type) {

        RequestTypeEnum requestTypeEnum = RequestTypeEnum.fromString(type);

        ArrayList<?> requestList = requestService.getRequestUnhandled(requestTypeEnum).getData();

        return ResponseEntity.ok(requestList);
    }

    @PostMapping(value = "/admin-request/rental-request-approve", produces = "application/text; charset=utf8")
    public ResponseEntity<?> approveRentalRequest(@RequestBody ApproveDTO approveDTO) {

        requestService.approveRequest(approveDTO, RequestTypeEnum.RENTAL);

        return ResponseEntity.ok("승인되었습니다.");
    }

    @PostMapping(value = "/admin-request/rental-request-reject", produces = "application/text; charset=utf8")
    public ResponseEntity<?> rejectRentalRequest(@RequestBody RentalRejectDTO rentalRejectDTO) {

        requestService.rejectRequest(rentalRejectDTO);
        
        return ResponseEntity.ok("승인 거부되었습니다.");
    }

    @PostMapping(value = "/admin-request/return-request-approve", produces = "application/text; charset=utf8")
    public ResponseEntity<?> approveReturnRequest(@RequestBody ApproveDTO approveDTO) {

        requestService.approveRequest(approveDTO, RequestTypeEnum.RETURN);

        return ResponseEntity.ok("반납 처리 완료");
    }

}
