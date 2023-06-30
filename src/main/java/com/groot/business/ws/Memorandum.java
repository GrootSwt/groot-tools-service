package com.groot.business.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.business.bean.request.base.WSRequest;
import com.groot.business.bean.response.base.WSResponse;
import com.groot.business.bean.MemorandumOperationTypeEnum;
import com.groot.business.model.User;
import com.groot.business.utils.WSUtil;
import com.groot.business.ws.handler.MemorandumAppendHandler;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多端长连接备忘录
 */
@Slf4j
@Component
public class Memorandum implements WebSocketHandler {

    private static final AtomicInteger sessionNumber = new AtomicInteger(0);

    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    private final MemorandumAppendHandler memorandumAppendHandler;

    public Memorandum(final MemorandumAppendHandler memorandumAppendHandler) {
        this.memorandumAppendHandler = memorandumAppendHandler;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> protocols = handshakeHeaders.get("Sec-WebSocket-Protocol");
        if (null != protocols && !protocols.isEmpty()) {
            sessions.add(session);
            sessionNumber.addAndGet(1);
            log.info(WSUtil.getUserFromProtocols(session).getDisplayName() + " 备忘录服务连接成功，当前连接数：" + sessionNumber.get());
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        User user = WSUtil.getUserFromProtocols(session);
        if (message.getPayload() instanceof String) {
            if (MemorandumOperationTypeEnum.HEARTBEAT.getValue().equals(message.getPayload())) {
                log.info("备忘录客户端<" + user.getDisplayName() + ">心跳检测");
                session.sendMessage(message);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                WSRequest<MemorandumOperationTypeEnum, ?> request = objectMapper.readValue(
                        (String) message.getPayload(), new TypeReference<WSRequest<MemorandumOperationTypeEnum, ?>>() {
                        });
                // 追加
                if (MemorandumOperationTypeEnum.APPEND.getValue().equals(request.getOperationType().getValue())) {
                    memorandumAppendHandler.handler(user, session, message, sessions);
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
        log.info(WSUtil.getUserFromProtocols(session).getDisplayName() + " 备忘录服务断开连接，当前连接数：" + sessionNumber.get());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public static void heartbeat() {
        WSUtil.heartbeat(sessions, sessionNumber, "备忘录服务");
    }

    public static void sendAll(List<com.groot.business.model.Memorandum> memorandums) throws IOException {
        String userId = StpUtil.getLoginIdAsString();
        for (WebSocketSession session : sessions) {
            if (WSUtil.getUserFromProtocols(session).getId().equals(userId)) {
                ObjectMapper objectMapper = new ObjectMapper();
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                        WSResponse.success("获取消息列表成功", MemorandumOperationTypeEnum.REPLACE, memorandums))));
            }
        }
    }

}