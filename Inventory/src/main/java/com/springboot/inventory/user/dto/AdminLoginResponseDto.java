package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
@Builder
public class AdminLoginResponseDto extends AdminResultDto {
    //    private Boolean checkDept;
    //    private UserRoleEnum userRole;
    private String token;
    private HttpServletResponse response;

}
