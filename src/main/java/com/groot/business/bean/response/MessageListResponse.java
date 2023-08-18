package com.groot.business.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageListResponse {

    private Boolean hasPrev;

    private List<MessageResponse> messageList;
}
