package com.chat.base.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ListResult<T> extends BaseResult {

    private List<T> data;


    public ListResult(String message) {
        super(message);
    }

    public ListResult(String message, List<T> data) {
        super(message);
        this.data = data;
    }

    public static <T> ListResult<T> success(String message, List<T> data) {
        return new ListResult<>(message, data);
    }

    public static <T> ListResult<T> failure(String message, List<T> data) {
        return new ListResult<>(message, data);
    }
}
