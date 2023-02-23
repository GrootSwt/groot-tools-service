package com.chat.business.service;

import com.chat.business.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> listByUserId(String userId);
    void save(Message message);
}
