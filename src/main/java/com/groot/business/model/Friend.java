package com.groot.business.model;

import com.groot.base.model.BaseModel;
import com.groot.business.bean.RelationStatusEnum;
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
    private RelationStatusEnum status;
    /**
     * 备注名
     */
    private String commentName;
}
