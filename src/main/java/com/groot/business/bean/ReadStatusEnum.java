package com.groot.business.bean;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReadStatusEnum {
    /**
     * 未读
     */
    @JsonProperty(value = "unread")
    UNREAD("unread"),
    /**
     * 已读
     */
    @JsonProperty(value = "read")
    READ("read");
    @EnumValue
    private final String value;

    ReadStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
