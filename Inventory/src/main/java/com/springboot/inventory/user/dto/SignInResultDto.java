package com.springboot.inventory.user.dto;

import lombok.*;

import javax.servlet.http.HttpServletResponse;

@Data
@NoArgsConstructor @AllArgsConstructor
@ToString
public class SignInResultDto extends SignUpResultDto {
    private String token;
    private HttpServletResponse response;

    @Builder
    public SignInResultDto(boolean success, int code, String msg, String token, HttpServletResponse response) {
        super(success, code, msg);
        this.token = token;
        this.response = response;
    }
}
