package com.springboot.inventory.common.enums;

public enum ResponseEnum {
    SUCCESS(0, "Success"), FAIL(-1, "Fail");

    int code;
    String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
