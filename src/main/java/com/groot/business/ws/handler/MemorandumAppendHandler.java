package com.groot.business.ws.handler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import com.groot.business.bean.enums.MemorandumContentType;
import com.groot.business.mapper.MemorandumMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.business.bean.request.base.WSRequest;
import com.groot.business.bean.response.base.WSResponse;
import com.groot.business.bean.enums.MemorandumOperationType;
import com.groot.business.model.Memorandum;
import com.groot.business.model.User;
import com.groot.business.service.MemorandumService;
import com.groot.business.utils.WSUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemorandumAppendHandler {

    private final MemorandumMapper memorandumMapper;

    private final ObjectMapper objectMapper;

    public MemorandumAppendHandler(final MemorandumMapper memorandumMapper, final ObjectMapper objectMapper) {
        this.memorandumMapper = memorandumMapper;
        this.objectMapper = objectMapper;
    }

    public void handler(User user, WebSocketSession session, WebSocketMessage<?> message,
                        CopyOnWriteArraySet<WebSocketSession> sessions) throws IOException {
        WSRequest<MemorandumAppendParams> request = objectMapper.readValue(
                (String) message.getPayload(),
                new TypeReference<>() {
                });
        log.info("备忘录内容：" + message.getPayload());
        Memorandum data = new Memorandum();
        data.setUserId(user.getId());
        data.setContent(request.getParams().getContent());
        data.setContentType(MemorandumContentType.TEXT);
        memorandumMapper.insert(data);
        log.info(user.getDisplayName() + "群发备忘录：" + message.getPayload());
        sessions.forEach(s -> {
            try {
                if (WSUtil.getUserFromProtocols(s).getId().equals(user.getId())) {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                            WSResponse.success("添加备忘录成功", MemorandumOperationType.APPEND, data))));
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
    }

}

@Data
class MemorandumAppendParams {
    private String content;
}
