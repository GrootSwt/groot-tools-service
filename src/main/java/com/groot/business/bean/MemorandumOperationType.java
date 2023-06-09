package com.groot.business.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MemorandumOperationType {
    @JsonProperty(value = "append")
    append("append"),
    @JsonProperty(value = "replace")
    replace("replace"),
    @JsonProperty(value = "heartbeat")
    heartbeat("heartbeat");

    private final String value;

    MemorandumOperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
