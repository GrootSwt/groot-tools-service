package com.groot.business.service.impl;

import com.groot.base.config.CurrentUserStore;
import com.groot.business.bean.ReadStatusEnum;
import com.groot.business.dto.MessageDTO;
import com.groot.business.mapper.MessageMapper;
import com.groot.business.model.Message;
import com.groot.business.service.MessageService;
import com.groot.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;
    private final UserService userService;
    private final CurrentUserStore currentUserStore;

    @Autowired
    public MessageServiceImpl(final MessageMapper messageMapper, final UserService userService, final CurrentUserStore currentUserStore) {
        this.messageMapper = messageMapper;
        this.userService = userService;
        this.currentUserStore = currentUserStore;
    }

    @Override
    public List<MessageDTO> listMessageByFriendId(String friendId) {
        userService.checkLoginStatus();
        String userId = currentUserStore.getCurrentUser().getUserId();
        return messageMapper.listMessageBySenderIdAndReceiverId(userId, friendId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MessageDTO> updateMessageRead(String senderId, String receiverId) {
        messageMapper.updateMessageRead(senderId, receiverId);
        return messageMapper.listMessageBySenderIdAndReceiverId(senderId, receiverId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MessageDTO> addMessage(String senderId, String receiverId, String messageContent) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(messageContent);
        message.setReadStatus(ReadStatusEnum.UNREAD);
        messageMapper.insert(message);
        return messageMapper.listMessageBySenderIdAndReceiverId(senderId, receiverId);
    }
}
