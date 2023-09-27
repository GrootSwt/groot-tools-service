package com.groot.business.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.groot.business.model.base.BaseModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("file")
public class FileModel extends BaseModel {

    private String originalName;

    private String locationUrl;
}
