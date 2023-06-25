package com.groot.business.controller;

import com.groot.base.bean.result.Response;
import com.groot.business.dto.MessageDTO;
import com.groot.business.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(final MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/{friendId}/listMessageByFriendId")
    public Response<List<MessageDTO>> listMessageByFriendId(@PathVariable String friendId) {
        List<MessageDTO> messageDTOList = messageService.listMessageByFriendId(friendId);
        return Response.success("获取消息列表成功", messageDTOList);
    }

}
