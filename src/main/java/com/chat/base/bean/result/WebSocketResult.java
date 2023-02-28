package com.chat.base.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WebSocketResult<T> extends BaseResult {

    private Integer code = 200;

    private T data;

    public WebSocketResult(Status status, String message) {
        super(status, message);
    }

    public WebSocketResult(Status status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    public WebSocketResult(Status status, String message, Integer code) {
        super(status, message);
        this.code = code;
    }

    public WebSocketResult(Status status, String message, Integer code, T data) {
        super(status, message);
        this.code = code;
        this.data = data;
    }

    public static <T> WebSocketResult<T> success(String message, T data) {
        return new WebSocketResult<T>(Status.success, message, data);
    }

    public static <T> WebSocketResult<T> failure(Integer code, String message) {
        return new WebSocketResult<T>(Status.failure, message, code);
    }
}
