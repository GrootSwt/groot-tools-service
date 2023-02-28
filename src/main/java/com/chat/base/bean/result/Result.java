package com.chat.base.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Result<T> extends BaseResult {

    private T data;

    public Result(Status status, String message) {
        super(status, message);
    }

    public Result(Status status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    public static <T> Result<T> failure(String message, T data) {
        return new Result<>(Status.failure, message, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(Status.success, message, data);
    }
}
