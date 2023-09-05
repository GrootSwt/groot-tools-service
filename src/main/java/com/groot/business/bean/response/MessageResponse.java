package com.groot.business.bean.response;

import com.groot.business.bean.enums.ReadStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private String id;

    private String senderId;

    private String receiverId;

    private String content;

    private ReadStatus readStatus;

    private Date createTime;
}
