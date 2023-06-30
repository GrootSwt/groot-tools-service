package com.groot.business.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.business.bean.request.base.WSRequest;
import com.groot.business.bean.ChatOperationTypeEnum;
import com.groot.business.model.User;
import com.groot.business.utils.WSUtil;
import com.groot.business.ws.handler.SendMessageHandler;
import com.groot.business.ws.handler.UpdateMessageToReadHandler;

import io.micrometer.common.lang.NonNullApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@NonNullApi
public class Chat implements WebSocketHandler {

    private static final AtomicInteger sessionNumber = new AtomicInteger(0);
    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    private final SendMessageHandler sendMessageHandler;
    private final UpdateMessageToReadHandler updateMessageToReadHandler;

    public Chat(final UpdateMessageToReadHandler updateMessageToReadHandler,
                SendMessageHandler sendMessageHandler) {
        this.updateMessageToReadHandler = updateMessageToReadHandler;
        this.sendMessageHandler = sendMessageHandler;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> protocols = handshakeHeaders.get("Sec-WebSocket-Protocol");
        if (null != protocols && !protocols.isEmpty()) {
            sessions.add(session);
            sessionNumber.addAndGet(1);
            log.info(WSUtil.getUserFromProtocols(session).getDisplayName() + " 聊天服务连接成功，当前连接数：" + sessionNumber.get());
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        User user = WSUtil.getUserFromProtocols(session);
        if (message.getPayload() instanceof String) {
            // 客户端心跳检测
            if (ChatOperationTypeEnum.HEARTBEAT.getValue().equals(message.getPayload())) {
                log.info("聊天客户端<" + user.getDisplayName() + ">心跳检测");
                session.sendMessage(message);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                WSRequest<ChatOperationTypeEnum, ?> requestTemp = objectMapper.readValue(
                        (String) message.getPayload(),
                        new TypeReference<>() {
                        });
                // 消息已读
                if (ChatOperationTypeEnum.READ.equals(requestTemp.getOperationType())) {
                    updateMessageToReadHandler.handler(session, message, sessions);
                }
                // 发送消息
                if (ChatOperationTypeEnum.SEND.equals(requestTemp.getOperationType())) {
                    sendMessageHandler.handler(session, message, sessions);
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
        log.info(WSUtil.getUserFromProtocols(session).getDisplayName() + " 聊天服务断开连接，当前连接数：" + sessionNumber.get());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public static void heartbeat() {
        WSUtil.heartbeat(sessions, sessionNumber, "聊天服务");
    }
}
