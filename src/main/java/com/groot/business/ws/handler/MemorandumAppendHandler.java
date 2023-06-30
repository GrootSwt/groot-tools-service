package com.groot.business.ws.handler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.business.bean.request.base.WSRequest;
import com.groot.business.bean.response.base.WSResponse;
import com.groot.business.bean.MemorandumOperationTypeEnum;
import com.groot.business.model.Memorandum;
import com.groot.business.model.User;
import com.groot.business.service.MemorandumService;
import com.groot.business.utils.WSUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemorandumAppendHandler {

  private final MemorandumService memorandumService;

  public MemorandumAppendHandler(final MemorandumService memorandumService) {
    this.memorandumService = memorandumService;
  }

  public void handler(User user, WebSocketSession session, WebSocketMessage<?> message,
      CopyOnWriteArraySet<WebSocketSession> sessions) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    WSRequest<MemorandumOperationTypeEnum, MemorandumAppendParams> request = objectMapper.readValue(
        (String) message.getPayload(),
        new TypeReference<WSRequest<MemorandumOperationTypeEnum, MemorandumAppendParams>>() {
        });
    log.info("备忘录内容：" + message.getPayload());
    Memorandum data = new Memorandum();
    data.setUserId(user.getId());
    data.setContent(request.getParams().getContent());
    memorandumService.save(data);
    log.info(user.getDisplayName() + "群发备忘录：" + message.getPayload());
    sessions.forEach(s -> {
      try {
        if (WSUtil.getUserFromProtocols(s).getId().equals(user.getId())) {
          s.sendMessage(new TextMessage(objectMapper.writeValueAsString(
              WSResponse.success("添加备忘录成功", MemorandumOperationTypeEnum.APPEND, data))));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

}

@Data
class MemorandumAppendParams {
  private String content;
}
