package com.groot.business.bean.result;

import com.groot.base.bean.result.BaseResult;
import com.groot.business.bean.MemorandumOperationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemorandumResult<T> extends BaseResult {

    private Integer code = 200;

    private T data;

    private MemorandumOperationType operationType;

    public MemorandumResult(String message) {
        super(message);
    }

    public MemorandumResult(MemorandumOperationType operationType, String message, T data) {
        super(message);
        this.operationType = operationType;
        this.data = data;
    }

    public MemorandumResult(String message, T data) {
        super(message);
        this.data = data;
    }

    public MemorandumResult(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public MemorandumResult(Integer code, String message, T data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public static <T> MemorandumResult<T> success(MemorandumOperationType operationType, String message, T data) {
        return new MemorandumResult<>(operationType, message, data);
    }

    public static <T> MemorandumResult<T> failure(Integer code, String message) {
        return new MemorandumResult<>(code, message);
    }
}
