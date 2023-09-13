package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("학생"),
    ADMIN("매니저"),
    MASTER("마스터");

    private final String korean;

    UserRole(String korean) {

        this.korean = korean;
    }

}