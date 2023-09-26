package com.springboot.inventory.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpResultDto {
    private boolean success;
    private int code;
    private String msg;
    private Map<String, String> fieldErrors;  // 실패한 필드와 메시지

    // 추가적인 생성자 등을 필요에 따라 구현할 수 있습니다.
    public void addFieldError(String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new HashMap<>();
        }
        fieldErrors.put(field, message);
    }
}