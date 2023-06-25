package com.groot.business.model;

import com.groot.base.model.BaseModel;
import com.groot.business.bean.ReadStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 消息
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends BaseModel {
    /**
    * 消息发送方id
    */
    private String senderId;
    /**
    * 接收方id
    */
    private String receiverId;
    /**
    * 消息读取状态
    */
    private ReadStatusEnum readStatus;
    /**
    * 消息内容
    */
    private String content;
}
