package com.springboot.inventory.supply.controller;

import com.springboot.inventory.common.userDetails.CustomUserDetails;
import com.springboot.inventory.supply.dto.SupplyRegisterDTO;
import com.springboot.inventory.supply.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supply-api")
public class SupplyRestController {

    private final SupplyService supplyService;

    @Autowired
    public SupplyRestController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping(value = "register-supply")
    @ResponseBody
    public ResponseEntity<?> registerSupply(@RequestBody SupplyRegisterDTO supplyRegisterDTO,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {

        supplyService.registerSupply(supplyRegisterDTO, userDetails);

        return ResponseEntity.ok("등록 완료");
    }

}
