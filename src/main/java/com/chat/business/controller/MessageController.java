package com.chat.business.controller;

import com.chat.base.bean.result.ListResult;
import com.chat.business.model.Message;
import com.chat.business.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/{userId}/listMessageByUserId")
    public ListResult<Message> listMessageByUserId(@PathVariable String userId) {
        List<Message> messages = messageService.listByUserId(userId);
        return ListResult.success("获取消息列表成功！", messages);
    }

}
