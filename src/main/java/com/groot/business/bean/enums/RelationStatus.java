package com.groot.business.bean.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 用户关系状态
 */
@Getter
public enum RelationStatus {
    /**
     * 关联
     */
    FRIEND(1, "friend"),
    /**
     * 删除
     */
    DELETED(2, "deleted");

    @EnumValue
    private final int value;
    private final String desc;

    RelationStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
