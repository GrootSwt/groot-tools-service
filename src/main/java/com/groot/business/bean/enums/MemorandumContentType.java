package com.groot.business.bean.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum MemorandumContentType {
    /**
     * 增加
     */
    TEXT(1, "text"),
    /**
     * 心跳检测
     */
    FILE(2, "file");

    @EnumValue
    private final int value;
    private final String desc;

    MemorandumContentType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
