package com.en.adback.entity.adreplace;

public enum ResponseEnum {
    EXISTED(1,""),SUCCESS(2,""),FAILED(3,"");

    public int code;
    public String message;
    ResponseEnum(int code, String message) {
     this.code=code;
     this.message=message;
    }
}
