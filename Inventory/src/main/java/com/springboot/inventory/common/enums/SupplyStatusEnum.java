package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum SupplyStatusEnum {
    USING("사용중"),
    REPAIRING("수리중"),
    STOCK("재고");

    private final String people;

    SupplyStatusEnum(String people) {
        this.people = people;
    }
}
