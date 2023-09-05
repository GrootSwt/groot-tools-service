package com.groot.business.bean.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ChatOperationType {
    /**
     * 心跳检测
     */
    HEARTBEAT(1, "heartbeat"),
    /**
     * 已读
     */
    READ(2, "read"),

    /**
     * 发送
     */
    SEND(3, "send");

    @EnumValue
    private final int value;
    private final String desc;

    ChatOperationType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
