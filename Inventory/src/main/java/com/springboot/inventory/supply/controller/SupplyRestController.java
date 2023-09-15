package com.springboot.inventory.supply.controller;

import com.springboot.inventory.common.userDetails.CustomUserDetails;
import com.springboot.inventory.supply.dto.SupplyRegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supply-api")
public class SupplyRestController {

    @PostMapping(value = "register-supply")
    @ResponseBody
    public ResponseEntity<?> registerSupply(@RequestBody SupplyRegisterDTO SupplyRegisterDTO,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {





        return ResponseEntity.ok("");
    }

}
