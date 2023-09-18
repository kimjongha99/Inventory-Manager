package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    USER(Authority.USER, "유저"),
    ADMIN(Authority.ADMIN, "관리자"),
    MASTER(Authority.MASTER, "마스터");

    private final String role;
    private final String text;

    UserRoleEnum(String role, String text) {
        this.role = role;
        this.text = text;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String MASTER = "ROLE_MASTER";

    }

}


