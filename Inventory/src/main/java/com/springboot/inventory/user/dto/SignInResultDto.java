package com.springboot.inventory.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  // Lombok의 Builder 패턴을 활성화
public class SignInResultDto extends SignUpResultDto {
    private String token;
    private HttpServletResponse response;
}