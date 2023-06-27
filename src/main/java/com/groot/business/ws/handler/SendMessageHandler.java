package com.groot.business.ws.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.base.bean.result.ws.WSResponse;
import com.groot.base.bean.result.ws.WSRequest;
import com.groot.business.bean.ChatOperationTypeEnum;
import com.groot.business.dto.MessageDTO;
import com.groot.business.model.User;
import com.groot.business.service.MessageService;
import com.groot.business.utils.WSUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class SendMessageHandler {

    private final MessageService messageService;

    public SendMessageHandler(final MessageService messageService) {
        this.messageService = messageService;
    }

    public void handler(WebSocketSession session, WebSocketMessage<?> message,
            CopyOnWriteArraySet<WebSocketSession> sessions) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        WSRequest<ChatOperationTypeEnum, SendMessageParams> request = objectMapper
                .readValue(
                        (String) message.getPayload(),
                        new TypeReference<WSRequest<ChatOperationTypeEnum, SendMessageParams>>() {
                        });
        String senderId = request.getParams().getSenderId();
        String receiverId = request.getParams().getReceiverId();
        String messageContent = request.getParams().getMessage();
        List<MessageDTO> messageDTOList = messageService.addMessage(senderId, receiverId, messageContent);
        session.sendMessage(
                new TextMessage(objectMapper.writeValueAsString(WSResponse.success(
                        "发送消息成功",
                        ChatOperationTypeEnum.SEND,
                        new SendMessageData(senderId, receiverId, messageDTOList)))));
        sessions.forEach(s -> {
            try {
                User receiver = WSUtil.getUserFromProtocols(s);
                if (receiver.getId().equals(receiverId)) {
                    s.sendMessage(new TextMessage(
                            objectMapper.writeValueAsString(WSResponse.success(
                                    "接收消息成功",
                                    ChatOperationTypeEnum.SEND,
                                    new SendMessageData(senderId, receiverId, messageDTOList)))));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

@Data
@AllArgsConstructor
class SendMessageData {
    private String senderId;

    private String receiverId;

    private List<MessageDTO> messageList;
}

@Data
class SendMessageParams {
    private String senderId;

    private String receiverId;

    private String message;
}