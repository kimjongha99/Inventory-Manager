package com.springboot.inventory.request.dto;

import lombok.Data;

@Data
public class ApproveDTO {

    private String requestId;

    private String supplyId;

    // if type is 'return', requestId
    private String pastRequestId;

}
