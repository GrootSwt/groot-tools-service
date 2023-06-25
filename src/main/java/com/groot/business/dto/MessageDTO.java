package com.groot.business.dto;

import com.groot.business.bean.ReadStatusEnum;
import lombok.Data;

@Data
public class MessageDTO {
    private String id;

    private String senderId;

    private String receiverId;

    private String content;

    private ReadStatusEnum readStatus;
}
