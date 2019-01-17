package com.en.adback.entity.adreplace;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author YSH
 * @crerate 201812
 * @desc 统一返回实体类
 */
@Data
@Component
public class ResponseModel {
    public int getCode() {
        return code;
    }

    public ResponseModel setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseModel setData(Object data) {
        this.data = data;
        return this;
    }

    public int code;
    public String name;
    public String message;
    public Object data;

    public ResponseModel(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseModel() {}

    public ResponseModel(ResponseEnum  responseEnum) {
        this.code = responseEnum.code ;
        this.name = responseEnum.name();
        this.message = responseEnum.message ;
    }


}
