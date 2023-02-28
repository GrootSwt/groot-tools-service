package com.chat.business.service;

import com.chat.business.model.Message;

import java.io.IOException;
import java.util.List;

public interface MessageService {

    List<Message> listByUserId(String userId);
    void save(Message message);

    void deleteMessageById(String id, String userId) throws IOException;
}
