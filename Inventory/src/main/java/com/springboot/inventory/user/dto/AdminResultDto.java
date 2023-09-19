package com.springboot.inventory.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.servlet.http.HttpServletResponse;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminResultDto  {
    private boolean success;
    private int code;
    private String msg;
}
