package com.groot.business.bean.response.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response<T> {

    private String message;

    private T data;

    private Response(String message) {
        this.message = message;
    }

    private Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> failure(String message) {
        return new Response<>(message);
    }

    public static <T> Response<T> success(String message) {
        return new Response<>(message);
    }

    public static <T> Response<T> failure(String message, T data) {
        return new Response<>(message, data);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(message, data);
    }
}
