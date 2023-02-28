package com.chat.business.controller;

import com.chat.base.bean.result.BaseResult;
import com.chat.base.bean.result.ListResult;
import com.chat.business.model.Message;
import com.chat.business.service.MessageService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ListResult<Message> listMessageByUserId(@NotBlank(message = "用户ID不可为空") @PathVariable String userId) {
        List<Message> messages = messageService.listByUserId(userId);
        return ListResult.success("获取消息列表成功！", messages);
    }

    @DeleteMapping(value = "{id}/{userId}/deleteMessageById")
    public BaseResult deleteMessageById(@NotBlank(message = "消息ID不可为空") @PathVariable String id,
                                        @NotBlank(message = "用户ID不可为空") @PathVariable String userId) throws IOException {
        messageService.deleteMessageById(id, userId);
        return BaseResult.success("删除成功");
    }

}
