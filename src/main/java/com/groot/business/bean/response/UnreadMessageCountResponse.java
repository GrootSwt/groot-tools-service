package com.groot.business.bean.response;

import lombok.Data;

@Data
public class UnreadMessageCountResponse {

    private String senderId;

    private String receiverId;

    private Integer count;
}
