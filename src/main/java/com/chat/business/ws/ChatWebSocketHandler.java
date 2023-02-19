package com.chat.business.ws;

import com.alibaba.fastjson2.JSON;
import com.chat.business.model.Message;
import com.chat.business.model.User;
import com.chat.business.service.MessageService;
import com.chat.business.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    private final MessageService messageService;

    private final AtomicInteger sessionNumber = new AtomicInteger(0);

    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public ChatWebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> protocols = handshakeHeaders.get("Sec-WebSocket-Protocol");
        if (null != protocols && !protocols.isEmpty()) {
            User user = JWTUtil.verifyToken(protocols.get(0));
            List<Message> messages = messageService.listByUserId(user.getId());
            TextMessage message = new TextMessage(JSON.toJSONString(messages));
            session.sendMessage(message);
            sessions.add(session);
            sessionNumber.addAndGet(1);
            logger.info("连接成功，当前连接数：" + sessionNumber.get());
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
        sessionNumber.addAndGet(-1);
        logger.info("断开连接，当前连接数：" + sessionNumber.get());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
