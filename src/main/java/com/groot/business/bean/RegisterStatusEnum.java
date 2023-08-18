package com.groot.business.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 注册申请状态
 */
public enum RegisterStatusEnum {
    /**
     * 未审批
     */
    @JsonProperty("unapproved")
    UNAPPROVED(1),
    /**
     * 同意
     */
    @JsonProperty("approved")
    APPROVED(2),
    /**
     * 拒绝
     */
    @JsonProperty("refused")
    REFUSED(3);


    private final int value;

    RegisterStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    }
