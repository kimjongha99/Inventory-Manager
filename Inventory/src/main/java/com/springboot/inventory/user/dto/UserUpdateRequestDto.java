package com.springboot.inventory.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserUpdateRequestDto {

    @NotBlank
    @Pattern(regexp = "[^ㄱ-ㅎㅏ-ㅣ]{1,30}$", message = "한글 자음/모음이 아닌 30자 이내")
    private String name;

    @NotNull
    private Long groupId;

    @NotBlank
    private String tel;

    @NotNull
    private Boolean alarm;
    private MultipartFile multipartFile;
}
