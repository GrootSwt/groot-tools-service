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

    private WebSocketOperationType operationType;

    public WebSocketResult(String message) {
        super(message);
    }

    public WebSocketResult(WebSocketOperationType operationType, String message, T data) {
        super(message);
        this.operationType = operationType;
        this.data = data;
    }

    public WebSocketResult(String message, T data) {
        super(message);
        this.data = data;
    }

    public WebSocketResult(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public WebSocketResult(Integer code, String message, T data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public static <T> WebSocketResult<T> success(WebSocketOperationType operationType, String message, T data) {
        return new WebSocketResult<>(operationType, message, data);
    }

    public static <T> WebSocketResult<T> failure(Integer code, String message) {
        return new WebSocketResult<>(code, message);
    }
}
