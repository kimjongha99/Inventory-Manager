package com.springboot.inventory.request.controller;

//
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.userDetails.CustomUserDetails;
import com.springboot.inventory.request.dto.RequestDTO;
import com.springboot.inventory.request.service.RequestService;

//
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request-api")
public class RequestRestController {

    private final RequestService requestService;

    public RequestRestController(RequestService registerRequest) {
        this.requestService = registerRequest;
    }

    @PostMapping(value = "/request")
    @ResponseBody
    public ResponseEntity<?> request(@RequestBody RequestDTO requestDTO,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();
        requestService.registerRequest(requestDTO, user);

        return ResponseEntity.ok("요청 완료");
    }
}
