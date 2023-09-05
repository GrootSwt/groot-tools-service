package com.groot.business.model;

import com.groot.business.model.base.BaseModel;
import com.groot.business.bean.enums.RelationStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户关系
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Friend extends BaseModel {
    /**
     * 主用户id
     */
    private String mainUserId;
    /**
     * 关联用户id
     */
    private String relationUserId;
    /**
     * 关联关系
     */
    private RelationStatus status;
    /**
     * 备注名
     */
    private String commentName;
}
