package com.groot.base.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response<T> extends BaseResponse {

    private T data;

    public Response(String message) {
        super(message);
    }

    public Response(String message, T data) {
        super(message);
        this.data = data;
    }

    public static <T> Response<T> failure(String message, T data) {
        return new Response<>(message, data);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(message, data);
    }
}
