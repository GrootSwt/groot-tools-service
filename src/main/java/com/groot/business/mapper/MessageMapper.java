package com.groot.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.groot.business.dto.MessageDTO;
import com.groot.business.model.Message;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {

    List<MessageDTO> listMessageBySenderIdAndReceiverId(String senderId, String receiverId);

    void updateMessageRead(String senderId, String receiverId);
    
}
