package com.groot.business.ws;

import com.groot.base.exception.WSRuntimeException;
import com.groot.business.bean.MemorandumOperationType;
import com.groot.business.bean.result.MemorandumResult;
import com.groot.business.model.Memorandum;
import com.groot.business.model.User;
import com.groot.business.service.MemorandumService;
import com.groot.business.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多端长连接备忘录
 */
@Slf4j
@Component
public class MemorandumHandler implements WebSocketHandler {

    private final MemorandumService memorandumService;

    private static final AtomicInteger sessionNumber = new AtomicInteger(0);

    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public MemorandumHandler(MemorandumService memorandumService) {
        this.memorandumService = memorandumService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> protocols = handshakeHeaders.get("Sec-WebSocket-Protocol");
        if (null != protocols && !protocols.isEmpty()) {
            JWTUtil.verifyToken(protocols.get(0), session);
            sessions.add(session);
            sessionNumber.addAndGet(1);
            log.info("连接成功，当前连接数：" + sessionNumber.get());
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        User user = getUserFromProtocols(session);
        if (message.getPayload() instanceof String) {
            if (MemorandumOperationType.heartbeat.getValue().equals(message.getPayload())) {
                session.sendMessage(message);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                Memorandum data = objectMapper.readValue((String) message.getPayload(), Memorandum.class);
                log.info("备忘录内容：" + message.getPayload());
                data.setUserId(user.getId());
                memorandumService.save(data);
                log.info(user.getUsername() + "群发备忘录：" + message.getPayload());
                sessions.forEach(s -> {
                    try {
                        if (getUserFromProtocols(s).getUsername().equals(user.getUsername())) {
                            s.sendMessage(new TextMessage(objectMapper.writeValueAsString(MemorandumResult.success(MemorandumOperationType.append, "群发消息", data))));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
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
                    iterator.next().sendMessage(new TextMessage(MemorandumOperationType.heartbeat.getValue()));
                } catch (IOException e) {
                    iterator.remove();
                }
            }
            sessionNumber.set(sessions.size());
            log.info("心跳检测后链接数量：" + sessions.size());
        }
    }

    public static void sendAll(String userId, List<Memorandum> memorandums) throws IOException {
        for (WebSocketSession session : sessions) {
            if (getUserFromProtocols(session).getId().equals(userId)) {
                ObjectMapper objectMapper = new ObjectMapper();
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(MemorandumResult.success(MemorandumOperationType.replace, "获取消息列表成功", memorandums))));
            }
        }
    }

    private static User getUserFromProtocols(WebSocketSession session) {
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> protocols = handshakeHeaders.get("Sec-WebSocket-Protocol");
        if (null != protocols && !protocols.isEmpty()) {
            User user = JWTUtil.verifyToken(protocols.get(0), session);
            if (StringUtils.hasText(user.getId()) && StringUtils.hasText(user.getUsername())) {
                return user;
            }
        }
        throw new WSRuntimeException(401, session, "用户信息校验失败");
    }
}