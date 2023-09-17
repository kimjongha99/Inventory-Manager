package com.springboot.inventory.request.controller;

//
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.userDetails.CustomUserDetails;
import com.springboot.inventory.request.dto.RentalRequestDTO;
import com.springboot.inventory.request.dto.RequestDTO;
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

    @PostMapping(value = "/user-request")
    @ResponseBody
    public ResponseEntity<?> sendRequest(@RequestBody RequestDTO requestDTO,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();
        requestService.registerRequest(requestDTO, user);

        return ResponseEntity.ok("요청 완료");
    }

    @GetMapping(value = "/admin-request/count")
    @ResponseBody
    public ResponseEntity<?> getRequestList(@RequestBody String type) {

        ArrayList<?> requestList = requestService.getRequestUnhandled(type).getData();

        return ResponseEntity.ok(requestList);
    }

    @PostMapping(value = "/admin-request/rental-request-accept")
    @ResponseBody
    public ResponseEntity<?> approveRentalRequest(@RequestBody RentalRequestDTO rentalRequestDTO) {

        String reqId = rentalRequestDTO.getRequestId();
        String supId = rentalRequestDTO.getSupplyId();

        requestService.updateSupplyState(reqId, supId, "rental");

        return ResponseEntity.ok("승인되었습니다.");
    }
}
