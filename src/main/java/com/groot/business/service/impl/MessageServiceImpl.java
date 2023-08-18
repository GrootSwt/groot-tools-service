package com.groot.business.service.impl;

import com.groot.business.bean.ReadStatusEnum;
import com.groot.business.bean.response.MessageResponse;
import com.groot.business.bean.response.MessageListResponse;
import com.groot.business.bean.response.UnreadMessageCountResponse;
import com.groot.business.mapper.MessageMapper;
import com.groot.business.model.Message;
import com.groot.business.service.MessageService;

import cn.dev33.satoken.stp.StpUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    public MessageServiceImpl(final MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Override
    public MessageListResponse listMessage(String friendId, String prevMessageId) {
        String userId = StpUtil.getLoginIdAsString();
        Integer pageSize = 30;
        if (prevMessageId == null) {
            List<MessageResponse> messageList = messageMapper.listMessage(pageSize, userId, friendId, null);
            return new MessageListResponse(messageList.size() == 30, messageList);
        }
        Long rowNumber = messageMapper.listMessageRowNumberById(userId, friendId, prevMessageId);
        List<MessageResponse> messageList = messageMapper.listMessage(pageSize, userId, friendId, rowNumber);
        return new MessageListResponse(rowNumber == 0L, messageList);
    }

    @Override
    public List<UnreadMessageCountResponse> listUnreadMessageCount(String friendId, String userId) {
        return messageMapper.listUnreadMessageCount(friendId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UnreadMessageCountResponse> updateMessageToRead(List<String> unreadMessageIds, String friendId, String userId) {
        messageMapper.updateMessageToRead(unreadMessageIds, friendId, userId);
        return this.listUnreadMessageCount(friendId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageResponse addMessage(String userId, String friendId, String content) {
        Message message = new Message();
        message.setSenderId(userId);
        message.setReceiverId(friendId);
        message.setContent(content);
        message.setReadStatus(ReadStatusEnum.UNREAD);
        messageMapper.insert(message);
        return new MessageResponse(
                message.getId(),
                userId,
                friendId,
                message.getContent(),
                message.getReadStatus(),
                message.getCreateTime()
        );
    }
}
