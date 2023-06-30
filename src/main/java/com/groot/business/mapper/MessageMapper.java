package com.groot.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.groot.business.dto.MessageDTO;
import com.groot.business.dto.UnreadMessageCountDTO;
import com.groot.business.model.Message;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {

    List<MessageDTO> listMessage(Integer pageSize, String senderId, String receiverId, Long rowNumber);

    List<UnreadMessageCountDTO> listUnreadMessageCount(String friendId, String userId);

    void updateMessageToRead(List<String> unreadMessageIds, String friendId, String userId);

    Long listMessageRowNumberById(String userId, String friendId, String prevMessageId);
}
