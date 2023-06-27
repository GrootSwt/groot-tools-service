package com.groot.business.ws.handler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

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

@Component
public class UpdateMessageToReadHandler {

  private final MessageService messageService;

  public UpdateMessageToReadHandler(final MessageService messageService) {
    this.messageService = messageService;
  }

  public void handler(WebSocketSession session, WebSocketMessage<?> message,
      CopyOnWriteArraySet<WebSocketSession> sessions) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    WSRequest<ChatOperationTypeEnum, UpdateMessageToReadParams> request = objectMapper.readValue(
        (String) message.getPayload(),
        new TypeReference<WSRequest<ChatOperationTypeEnum, UpdateMessageToReadParams>>() {
        });
    String senderId = request.getParams().getSenderId();
    String receiverId = request.getParams().getReceiverId();
    List<MessageDTO> messageDTOList = messageService.updateMessageRead(senderId, receiverId);
    session.sendMessage(
        new TextMessage(objectMapper.writeValueAsString(WSResponse.success(
            "消息已读成功",
            ChatOperationTypeEnum.READ,
            new UpdateMessageToReadData(senderId, receiverId, messageDTOList)))));
    sessions.forEach(s -> {
      try {
        User sender = WSUtil.getUserFromProtocols(s);
        if (sender.getId().equals(senderId)) {
          s.sendMessage(new TextMessage(objectMapper.writeValueAsString(WSResponse.success(
              "消息已读成功",
              ChatOperationTypeEnum.READ,
              new UpdateMessageToReadData(senderId, receiverId, messageDTOList)))));
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
  private String senderId;

  private String receiverId;

  private List<MessageDTO> messageList;
}

@Data
class UpdateMessageToReadParams {
  private String senderId;
  private String receiverId;
}