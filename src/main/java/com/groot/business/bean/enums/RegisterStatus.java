package com.groot.business.bean.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 注册申请状态
 */
@Getter
public enum RegisterStatus {
    /**
     * 未审批
     */
    UNAPPROVED(1, "unapproved"),
    /**
     * 同意
     */
    APPROVED(2, "approved"),
    /**
     * 拒绝
     */
    REFUSED(3, "refused");

    @EnumValue
    private final int value;
    private final String desc;

    RegisterStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
