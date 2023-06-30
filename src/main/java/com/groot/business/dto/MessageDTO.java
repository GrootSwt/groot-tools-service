package com.groot.business.dto;

import com.groot.business.bean.ReadStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String id;

    private String senderId;

    private String receiverId;

    private String content;

    private ReadStatusEnum readStatus;

    private Date createTime;
}
