package com.springboot.inventory.common.enums;

import lombok.Getter;

@Getter
public enum SupplyStatusEnum {

    USING("사용중"),REPAIRING("수리중"),STOCK("재고") ;

    private final String korean;

    SupplyStatusEnum(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }
    public static SupplyStatusEnum getByKorean(String korean) {
        for (SupplyStatusEnum status : values()) {
            if (status.getKorean().equals(korean)) {
                return status;
            }
        }
        return null; // 또는 기본값, 또는 예외를 던질 수 있습니다.
    }

}
