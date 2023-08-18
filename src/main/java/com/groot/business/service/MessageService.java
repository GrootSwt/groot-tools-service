package com.groot.business.service;

import com.groot.business.bean.response.MessageResponse;
import com.groot.business.bean.response.MessageListResponse;
import com.groot.business.bean.response.UnreadMessageCountResponse;

import java.util.List;

public interface MessageService {
    MessageListResponse listMessage(String friendId, String prevMessageId);


    List<UnreadMessageCountResponse> listUnreadMessageCount(String senderId, String receiverId);

    List<UnreadMessageCountResponse> updateMessageToRead(List<String> unreadMessageIds, String friendId, String userId);

    MessageResponse addMessage(String userId, String friendId, String message);
}
