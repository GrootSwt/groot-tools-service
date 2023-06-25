package com.groot.business.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户关系状态
 */
public enum RelationStatusEnum {
    /**
     * 关联
     */
    @JsonProperty(value = "friend")
    FRIEND("friend"),
    /**
     * 删除
     */
    @JsonProperty(value = "deleted")
    DELETED("deleted");

    private final String value;

    RelationStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
