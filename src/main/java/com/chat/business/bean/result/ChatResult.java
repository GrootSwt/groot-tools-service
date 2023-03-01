package com.chat.business.bean.result;

import com.chat.base.bean.result.BaseResult;
import com.chat.business.bean.ChatOperationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatResult<T> extends BaseResult {

    private Integer code = 200;

    private T data;

    private ChatOperationType operationType;

    public ChatResult(String message) {
        super(message);
    }

    public ChatResult(ChatOperationType operationType, String message, T data) {
        super(message);
        this.operationType = operationType;
        this.data = data;
    }

    public ChatResult(String message, T data) {
        super(message);
        this.data = data;
    }

    public ChatResult(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ChatResult(Integer code, String message, T data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public static <T> ChatResult<T> success(ChatOperationType operationType, String message, T data) {
        return new ChatResult<>(operationType, message, data);
    }

    public static <T> ChatResult<T> failure(Integer code, String message) {
        return new ChatResult<>(code, message);
    }
}
