package com.groot.business.schedule;

import com.groot.business.ws.MemorandumWebSocketHandler;
import com.groot.business.ws.Chat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatCheck {

    @Scheduled(fixedRate = 30000)
    public void heartbeatCheck() {
        MemorandumWebSocketHandler.heartbeat();
        Chat.heartbeat();
    }
}
