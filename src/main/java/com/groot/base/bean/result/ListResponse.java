package com.groot.base.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ListResponse<T> extends BaseResponse {

    private List<T> data;


    public ListResponse(String message) {
        super(message);
    }

    public ListResponse(String message, List<T> data) {
        super(message);
        this.data = data;
    }

    public static <T> ListResponse<T> success(String message, List<T> data) {
        return new ListResponse<>(message, data);
    }

    public static <T> ListResponse<T> failure(String message, List<T> data) {
        return new ListResponse<>(message, data);
    }
}
