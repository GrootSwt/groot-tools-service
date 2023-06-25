package com.groot.base.bean.result.ws;

import lombok.Data;

@Data
public class WSResponse<O, T> {
    private Integer status = 200;
    private String message;
    private O operationType;
    private T data;

    private WSResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    private WSResponse(Integer status, String message, O operationType) {
        this.status = status;
        this.message = message;
        this.operationType = operationType;
    }

    private WSResponse(Integer status, String message, O operationType, T data) {
        this.status = status;
        this.message = message;
        this.operationType = operationType;
        this.data = data;
    }

    public static <O, T> WSResponse<O, T> success(String message, O operationType, T data) {
        return new WSResponse<>(200, message, operationType, data);
    }

    public static <O, T> WSResponse<O, T> failure(Integer status, String message) {
        return new WSResponse<>(status, message);
    }

    public static <O, T> WSResponse<O, T> failure(Integer status, O operationType, String message) {
        return new WSResponse<>(status, message, operationType);
    }

    public static <O, T> WSResponse<O, T> failure(Integer status, String message, O operationType, T data) {
        return new WSResponse<>(status, message, operationType, data);
    }
}
