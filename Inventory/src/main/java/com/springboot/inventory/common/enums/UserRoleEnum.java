package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    USER(Authority.USER,"유저"),
    ADMIN(Authority.ADMIN,"관리자"),

    MASTER(Authority.MASTER,"마스터");

    private String authority; // 권한
    private String role; // 역할


    UserRoleEnum(String authority, String role){ // UserRoleEnum 생성자
        this.authority = authority;
        this.role = role;
    }

    public static class Authority{ // 권한
        public static final String USER = "ROLE_USER"; // 유저
        public static final String ADMIN = "ROLE_ADMIN"; // 어드민 (비품 관리자)
        public static final String MASTER = "ROLE_MASTER"; // 마스터 계정 - 권한 부여
    }



}
