package com.groot.business.bean.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum MemorandumOperationType {
    /**
     * 拼接
     */
    APPEND(1, "append"),
    /**
     * 替换
     */
    REPLACE(2, "replace"),
    /**
     * 增加
     */
    ADD(3, "add"),
    /**
     * 心跳检测
     */
    HEARTBEAT(4, "heartbeat");

    @EnumValue
    private final int value;
    private final String desc;

    MemorandumOperationType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
