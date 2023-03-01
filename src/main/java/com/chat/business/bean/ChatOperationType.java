package com.chat.business.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChatOperationType {
    @JsonProperty(value = "append")
    append("append"),
    @JsonProperty(value = "replace")
    replace("replace"),
    @JsonProperty(value = "heartbeat")
    heartbeat("heartbeat");

    private final String value;

    private ChatOperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
