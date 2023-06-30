package com.groot.business.dto;

import lombok.Data;

@Data
public class UnreadMessageCountDTO {

    private String senderId;

    private String receiverId;

    private Integer count;
}
