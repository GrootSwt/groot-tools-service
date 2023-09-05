package com.groot.business.bean.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ReadStatus {
    /**
     * 未读
     */
    UNREAD(1, "unread"),
    /**
     * 已读
     */
    READ(2, "read");
    @EnumValue
    private final int value;
    private final String desc;

    ReadStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
