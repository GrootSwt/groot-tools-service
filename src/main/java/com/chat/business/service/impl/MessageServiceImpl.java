package com.chat.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chat.business.mapper.MessageMapper;
import com.chat.business.model.Message;
import com.chat.business.service.MessageService;
import com.chat.business.ws.ChatHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }


    @Override
    public List<Message> listByUserId(String userId) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByAsc("create_time");
        return messageMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public void deleteMessageById(String id, String userId) throws IOException {
        Message message = new Message();
        message.setId(id);
        messageMapper.deleteById(message);
        List<Message> messages = this.listByUserId(userId);
        ChatHandler.sendAll(userId, messages);
    }
}
