package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum AcceptResult {
    ACCEPT("승인"), DECLINE("거절"), DISPOSE("폐기"), ASSIGN("사용자 배정");

    private final String people;

    AcceptResult(String people) {
        this.people = people;
    }
}
