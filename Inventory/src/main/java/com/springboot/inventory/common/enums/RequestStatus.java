package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum RequestStatus {
    UNPROCESSED("처리전"),
    PROCESSING("처리중"),
    PROCESSED("처리완료");

    private final String people;

    RequestStatus(String people) {
        this.people = people;
    }
}
