package com.groot.business.ws.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.business.bean.response.base.WSResponse;
import com.groot.business.bean.request.base.WSRequest;
import com.groot.business.bean.enums.ChatOperationType;
import com.groot.business.bean.response.MessageResponse;
import com.groot.business.bean.response.UnreadMessageCountResponse;
import com.groot.business.model.User;
import com.groot.business.service.MessageService;
import com.groot.business.utils.WSUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class SendMessageService {

    private final MessageService messageService;

    private final ObjectMapper objectMapper;

    public SendMessageService(final MessageService messageService,
                              final ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    public void handler(WebSocketSession session, WebSocketMessage<?> message,
            CopyOnWriteArraySet<WebSocketSession> sessions) throws IOException {
        WSRequest<SendMessageParams> request = objectMapper
                .readValue(
                        (String) message.getPayload(),
                        new TypeReference<>() {
                        });
        String userId = WSUtil.getUserFromProtocols(session).getId();
        String friendId = request.getParams().getFriendId();
        String content = request.getParams().getMessage();
        MessageResponse messageResponse = messageService.addMessage(userId, friendId, content);
        List<UnreadMessageCountResponse> unreadMessageCountResponseList = messageService.listUnreadMessageCount(userId, friendId);
        AtomicReference<Integer> userUnreadMessageCount = new AtomicReference<>(0);
        AtomicReference<Integer> friendUnreadMessageCount = new AtomicReference<>(0);
        unreadMessageCountResponseList.forEach(unreadMessageCountDTO -> {
            if (unreadMessageCountDTO.getReceiverId().equals(userId) && unreadMessageCountDTO.getSenderId().equals(friendId)) {
                userUnreadMessageCount.set(unreadMessageCountDTO.getCount());
            }
            if (unreadMessageCountDTO.getSenderId().equals(userId) && unreadMessageCountDTO.getReceiverId().equals(friendId)) {
                friendUnreadMessageCount.set(unreadMessageCountDTO.getCount());
            }
        });

        sessions.forEach(s -> {
            try {
                User loginedUser = WSUtil.getUserFromProtocols(s);
                if (loginedUser.getId().equals(friendId)) {
                    s.sendMessage(new TextMessage(
                            objectMapper.writeValueAsString(WSResponse.success(
                                    "接收消息成功",
                                    ChatOperationType.SEND,
                                    new SendMessageData(userId, messageResponse, friendUnreadMessageCount.get())))));
                }
                if (loginedUser.getId().equals(userId)) {
                    s.sendMessage(
                            new TextMessage(objectMapper.writeValueAsString(WSResponse.success(
                                    "发送消息成功",
                                    ChatOperationType.SEND,
                                    new SendMessageData(friendId, messageResponse, userUnreadMessageCount.get())))));
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        });
    }
}

@Data
@AllArgsConstructor
class SendMessageData {
    private String friendId;
    private MessageResponse message;
    private Integer unreadMessageCount;
}

@Data
class SendMessageParams {
    private String friendId;

    private String message;
}