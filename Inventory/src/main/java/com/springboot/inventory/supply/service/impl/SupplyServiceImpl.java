package com.springboot.inventory.supply.service.impl;

import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.supply.dto.SupplyRegisterDTO;
import com.springboot.inventory.supply.service.SupplyService;
import org.springframework.stereotype.Service;

@Service
public class SupplyServiceImpl implements SupplyService {

    public ResponseDTO<?> registerSupply(SupplyRegisterDTO supplyRegisterDTO) {




        return new ResponseDTO<>(true, null);
    }

}
