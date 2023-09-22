package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum RequestTypeEnum {

    RENTAL("rental"),
    RETURN("return");

    private final String type;

    RequestTypeEnum(String role) {
        this.type = role;
    }

    public static RequestTypeEnum fromString(String text) {
        for (RequestTypeEnum enumValue : RequestTypeEnum.values()) {
            if (enumValue.getType().equalsIgnoreCase(text)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("일치하는 요청 종류가 없습니다.");
    }
}
