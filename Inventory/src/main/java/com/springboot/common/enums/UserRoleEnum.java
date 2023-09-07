package com.springboot.common.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    USER("학생"),
    ADMIN("매니저"),
    MASTER("마스터");

    private final String korean;

    UserRoleEnum(String korean) {

        this.korean = korean;
    }

}