package com.chat.business.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageType {
    @JsonProperty(value = "text")
    text("text"),
    @JsonProperty(value = "link")
    link("link");
    private final String value;
    private MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
