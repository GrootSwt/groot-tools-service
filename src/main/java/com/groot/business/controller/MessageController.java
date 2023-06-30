package com.groot.business.controller;

import com.groot.business.bean.response.base.Response;
import com.groot.business.dto.MessageListDTO;
import com.groot.business.service.MessageService;

import cn.dev33.satoken.annotation.SaCheckLogin;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/message")
@SaCheckLogin
public class MessageController {

    private final MessageService messageService;

    public MessageController(final MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/{friendId}/listMessage")
    public Response<MessageListDTO> listMessage(@PathVariable String friendId,
                                                @RequestParam(required = false) String prevMessageId) {
        MessageListDTO messageDTOList = messageService.listMessage(friendId, prevMessageId);
        return Response.success("获取消息列表成功", messageDTOList);
    }

}
