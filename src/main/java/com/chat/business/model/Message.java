package com.chat.business.model;

import com.chat.base.model.BaseModel;
import com.chat.business.bean.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Message extends BaseModel {

    private String userId;

    private String message;

    private MessageType messageType;


}
