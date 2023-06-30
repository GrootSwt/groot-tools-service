package com.groot.business.service;

import com.groot.business.dto.MessageDTO;
import com.groot.business.dto.MessageListDTO;
import com.groot.business.dto.UnreadMessageCountDTO;

import java.util.List;

public interface MessageService {
    MessageListDTO listMessage(String friendId, String prevMessageId);


    List<UnreadMessageCountDTO> listUnreadMessageCount(String senderId, String receiverId);

    List<UnreadMessageCountDTO> updateMessageToRead(List<String> unreadMessageIds, String friendId, String userId);

    MessageDTO addMessage(String userId, String friendId, String message);
}
