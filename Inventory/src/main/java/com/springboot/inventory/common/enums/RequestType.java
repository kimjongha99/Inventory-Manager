package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum RequestType {
    SUPPLY("비품 요청"), REPAIR("수리 요청"), RETURN("반납 요청"), REPORT("보고서 결제");

    private final String people;

    RequestType(String people) {
        this.people = people;
    }
}
