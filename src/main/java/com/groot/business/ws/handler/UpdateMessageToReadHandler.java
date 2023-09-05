package com.groot.business.ws.handler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import com.groot.business.bean.response.UnreadMessageCountResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.business.bean.response.base.WSResponse;
import com.groot.business.bean.request.base.WSRequest;
import com.groot.business.bean.enums.ChatOperationType;
import com.groot.business.model.User;
import com.groot.business.service.MessageService;
import com.groot.business.utils.WSUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class UpdateMessageToReadHandler {

    private final MessageService messageService;

    private final ObjectMapper objectMapper;

    public UpdateMessageToReadHandler(final MessageService messageService,
                                      final ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    public void handler(WebSocketSession session, WebSocketMessage<?> message,
                        CopyOnWriteArraySet<WebSocketSession> sessions) throws IOException {
        WSRequest<UpdateMessageToReadParams> request = objectMapper.readValue(
                (String) message.getPayload(),
                new TypeReference<>() {
                });
        String friendId = request.getParams().getFriendId();
        String userId = WSUtil.getUserFromProtocols(session).getId();
        List<String> unreadMessageIds = request.getParams().getUnreadMessageIds();
        // 获取双方未读消息条数
        List<UnreadMessageCountResponse> unreadMessageCountResponseList = messageService.updateMessageToRead(unreadMessageIds, friendId, userId);
        AtomicReference<Integer> userUnreadMessageCount = new AtomicReference<>(0);
        AtomicReference<Integer> friendUnreadMessageCount = new AtomicReference<>(0);
        unreadMessageCountResponseList.forEach(unreadMessageCountDTO -> {
            if (unreadMessageCountDTO.getSenderId().equals(friendId) && unreadMessageCountDTO.getReceiverId().equals(userId)) {
                userUnreadMessageCount.set(unreadMessageCountDTO.getCount());
            }
            if (unreadMessageCountDTO.getSenderId().equals(userId) && unreadMessageCountDTO.getReceiverId().equals(friendId)) {
                friendUnreadMessageCount.set(unreadMessageCountDTO.getCount());
            }
        });
        session.sendMessage(
                new TextMessage(objectMapper.writeValueAsString(WSResponse.success(
                        "消息已读成功",
                        ChatOperationType.READ,
                        new UpdateMessageToReadData(friendId, unreadMessageIds, userUnreadMessageCount.get())))));
        sessions.forEach(s -> {
            try {
                User sender = WSUtil.getUserFromProtocols(s);
                if (sender.getId().equals(friendId)) {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(WSResponse.success(
                            "消息已读成功",
                            ChatOperationType.READ,
                            new UpdateMessageToReadData(userId, unreadMessageIds, friendUnreadMessageCount.get())))));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

@Data
@AllArgsConstructor
class UpdateMessageToReadData {
    private String friendId;

    private List<String> readMessageIds;

    private Integer unreadCount;
}

@Data
class UpdateMessageToReadParams {
    private String friendId;
    private List<String> unreadMessageIds;
}