package com.springboot.inventory.supply.service;

import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.supply.dto.SupplyRegisterDTO;

public interface SupplyService {
    ResponseDTO<?> registerSupply(SupplyRegisterDTO supplyRegisterDTO);

}
