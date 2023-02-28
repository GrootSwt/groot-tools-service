package com.chat.base.bean.result;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum WebSocketOperationType {
    @JsonProperty(value = "append")
    append("append"),
    @JsonProperty(value = "replace")
    replace("replace"),
    @JsonProperty(value = "heartbeat")
    heartbeat("heartbeat");

    private final String value;

    private WebSocketOperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
