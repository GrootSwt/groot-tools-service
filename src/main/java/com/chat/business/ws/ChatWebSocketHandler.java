package com.chat.business.ws;

import com.alibaba.fastjson2.JSON;
import com.chat.business.model.Message;
import com.chat.business.model.User;
import com.chat.business.service.MessageService;
import com.chat.business.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private static final String HEARTBEAT = "heartbeat";

    private final MessageService messageService;

    private static final AtomicInteger sessionNumber = new AtomicInteger(0);

    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public ChatWebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> protocols = handshakeHeaders.get("Sec-WebSocket-Protocol");
        if (null != protocols && !protocols.isEmpty()) {
            JWTUtil.verifyToken(protocols.get(0));
            sessions.add(session);
            sessionNumber.addAndGet(1);
            log.info("连接成功，当前连接数：" + sessionNumber.get());
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message.getPayload() instanceof String) {
            if (HEARTBEAT.equals(message.getPayload())) {
                session.sendMessage(message);
            } else {
                Message data = JSON.parseObject((String) message.getPayload(), Message.class);
                log.info("接收到的消息：" + message.getPayload());
                HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
                List<String> protocols = handshakeHeaders.get("Sec-WebSocket-Protocol");
                if (null != protocols && !protocols.isEmpty()) {
                    User user = JWTUtil.verifyToken(protocols.get(0));
                    data.setUserId(user.getId());
                    messageService.save(data);
                    log.info(user.getUsername() + "群发消息：" + message.getPayload());
                    sessions.forEach(s -> {
                        if (!s.equals(session)) {
                            try {
                                s.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
        sessionNumber.addAndGet(-1);
        log.info("断开连接，当前连接数：" + sessionNumber.get());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public static void heartbeat() {
        if (!sessions.isEmpty()) {
            Iterator<WebSocketSession> iterator = sessions.iterator();
            log.info("心跳检测前连接数量：" + sessions.size());
            while (iterator.hasNext()) {
                try {
                    iterator.next().sendMessage(new TextMessage(HEARTBEAT));
                } catch (IOException e) {
                    iterator.remove();
                }
            }
            sessionNumber.set(sessions.size());
            log.info("心跳检测后链接数量：" + sessions.size());
        }
    }
}
