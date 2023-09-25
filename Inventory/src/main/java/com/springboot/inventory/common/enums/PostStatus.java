package com.springboot.inventory.common.enums;

public enum PostStatus {
    APPROVED("승인"),
    PENDING("대기"),
    REJECTED("거절");

    private final String label;

    PostStatus(String label){
        this.label=label;
    }

    public String getLabel(){
        return label;
    }

}
