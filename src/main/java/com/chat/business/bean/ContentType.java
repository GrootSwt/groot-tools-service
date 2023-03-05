package com.chat.business.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ContentType {
    @JsonProperty(value = "text")
    text("text"),
    @JsonProperty(value = "link")
    link("link");
    private final String value;
    private ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
