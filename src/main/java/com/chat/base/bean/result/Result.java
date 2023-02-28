package com.chat.base.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Result<T> extends BaseResult {

    private T data;

    public Result(String message) {
        super(message);
    }

    public Result(String message, T data) {
        super(message);
        this.data = data;
    }

    public static <T> Result<T> failure(String message, T data) {
        return new Result<>(message, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(message, data);
    }
}
