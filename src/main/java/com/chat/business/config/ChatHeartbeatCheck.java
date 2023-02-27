package com.chat.business.config;

import com.chat.business.ws.ChatWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ChatHeartbeatCheck {

    @Scheduled(fixedRate = 30000)
    public void chatHeartbeatCheck() {
        ChatWebSocketHandler.heartbeat();
    }
}
