package com.groot.business.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.groot.business.bean.request.base.WSRequest;
import com.groot.business.bean.response.MemorandumResponse;
import com.groot.business.bean.response.base.WSResponse;
import com.groot.business.bean.enums.MemorandumOperationType;
import com.groot.business.model.User;
import com.groot.business.utils.WSUtil;
import com.groot.business.ws.handler.MemorandumAppendHandler;

import cn.dev33.satoken.stp.StpUtil;
import io.micrometer.common.lang.NonNullApi;
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
@NonNullApi
public class Memorandum implements WebSocketHandler {

    private static final AtomicInteger sessionNumber = new AtomicInteger(0);

    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    private final MemorandumAppendHandler memorandumAppendHandler;

    private final ObjectMapper objectMapper;

    public Memorandum(final MemorandumAppendHandler memorandumAppendHandler, final ObjectMapper objectMapper) {
        this.memorandumAppendHandler = memorandumAppendHandler;
        this.objectMapper = objectMapper;
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
            if (MemorandumOperationType.HEARTBEAT.getDesc().equals(message.getPayload())) {
                log.info("备忘录客户端<" + user.getDisplayName() + ">心跳检测");
                session.sendMessage(message);
            } else {
                WSRequest<?> request = objectMapper.readValue(
                        (String) message.getPayload(), new TypeReference<>() {
                        });
                // 追加
                if (MemorandumOperationType.APPEND.getDesc().equals(request.getOperationType())) {
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

    public static void sendAll(List<MemorandumResponse> memorandums) throws IOException {
        String userId = StpUtil.getLoginIdAsString();
        for (WebSocketSession session : sessions) {
            if (WSUtil.getUserFromProtocols(session).getId().equals(userId)) {
                ObjectMapper currentObjectMapper = new ObjectMapper();
                currentObjectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
                session.sendMessage(new TextMessage(currentObjectMapper.writeValueAsString(
                        WSResponse.success("获取消息列表成功", MemorandumOperationType.REPLACE, memorandums))));
            }
        }
    }

}