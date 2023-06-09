package com.groot.base.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class BaseResponse implements Serializable {

    protected String message;

    public BaseResponse(String message) {
        this.message = message;
    }

    public static BaseResponse success(String message) {
        return new BaseResponse(message);
    }

    public static BaseResponse failure(String message) {
        return new BaseResponse(message);
    }
}
