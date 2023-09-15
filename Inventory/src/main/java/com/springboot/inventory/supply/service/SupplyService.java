package com.springboot.inventory.supply.service;

import com.springboot.inventory.supply.domain.entity.Supply;
import com.springboot.inventory.supply.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {
    @Autowired
    SupplyRepository supplyRepository ;

    public List<Supply> supplyList(){
        return  supplyRepository.findAll();
    }
}
