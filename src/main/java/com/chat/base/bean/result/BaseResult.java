package com.chat.base.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class BaseResult implements Serializable {

    protected String message;

    public BaseResult(String message) {
        this.message = message;
    }

    public static BaseResult success(String message) {
        return new BaseResult(message);
    }

    public static BaseResult failure(String message) {
        return new BaseResult(message);
    }
}
