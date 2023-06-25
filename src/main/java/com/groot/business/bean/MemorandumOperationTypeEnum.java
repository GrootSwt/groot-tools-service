package com.groot.business.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MemorandumOperationTypeEnum {
    /**
     * 拼接
     */
    @JsonProperty(value = "append")
    APPEND("append"),
    /**
     * 替换
     */
    @JsonProperty(value = "replace")
    REPLACE("replace"),
    /**
     * 增加
     */
    ADD("add"),
    /**
     * 心跳检测
     */
    @JsonProperty(value = "heartbeat")
    HEARTBEAT("heartbeat");

    private final String value;

    MemorandumOperationTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
