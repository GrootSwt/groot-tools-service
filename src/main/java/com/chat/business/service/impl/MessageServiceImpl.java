package com.chat.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chat.business.mapper.MessageMapper;
import com.chat.business.model.Message;
import com.chat.business.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return messageMapper.selectList(wrapper);
    }
}
