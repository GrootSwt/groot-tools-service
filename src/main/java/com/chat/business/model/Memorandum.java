package com.chat.business.model;

import com.chat.base.model.BaseModel;
import com.chat.business.bean.ContentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Memorandum extends BaseModel {

    private String userId;

    private String content;

    private ContentType contentType;


}
