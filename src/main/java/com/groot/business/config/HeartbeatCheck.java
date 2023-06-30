package com.groot.business.config;

import com.groot.business.ws.Memorandum;
import com.groot.business.ws.Chat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatCheck {

    @Scheduled(fixedRate = 30000)
    public void heartbeatCheck() {
        Memorandum.heartbeat();
        Chat.heartbeat();
    }
}
