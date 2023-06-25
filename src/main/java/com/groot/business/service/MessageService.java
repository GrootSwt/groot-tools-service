package com.groot.business.service;

import com.groot.business.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    List<MessageDTO> listMessageByFriendId(String friendId);

    List<MessageDTO> updateMessageRead(String senderId, String receiverId);
    
    List<MessageDTO> addMessage(String senderId, String receiverId, String message);
}
