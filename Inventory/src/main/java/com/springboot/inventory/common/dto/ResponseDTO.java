package com.springboot.inventory.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ResponseDTO<T> {

    private Boolean result;

    private T data;

}
