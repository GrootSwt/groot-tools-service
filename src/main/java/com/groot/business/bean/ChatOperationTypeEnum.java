package com.groot.business.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChatOperationTypeEnum {
    /**
     * 心跳检测
     */
    @JsonProperty(value = "heartbeat")
    HEARTBEAT("heartbeat"),
    /**
     * 已读
     */
    @JsonProperty(value = "read")
    READ("read"),

    /**
     * 发送
     */
    @JsonProperty(value = "send")
    SEND("send");

    private final String value;

    ChatOperationTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
