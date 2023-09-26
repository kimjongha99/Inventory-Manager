package com.springboot.inventory.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank
//    @Pattern(regexp = "[^ㄱ-ㅎㅏ-ㅣ]{1,30}$", message = "한글 자/모음이 아닌 30자 이내")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String tel;
    @NotBlank
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,16}$",
//            message = "8~16자, 최소 하나의 문자, 하나의 숫자, 하나의 특수 문자, 공백 없음")
    @Pattern(regexp = "^[0-9]*${6}", message = "6자리 숫자를 입력해 주세요.")
    private String password;
    private String team;
}
