package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("사용자"),
    ADMIN("관리자"),
    MASTER("마스터");

    private final String korean;

    UserRole(String korean) {

        this.korean = korean;
    }

}