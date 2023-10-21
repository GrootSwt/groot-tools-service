package com.groot.business.schedule;

import com.groot.business.ws.MemorandumHandler;
import com.groot.business.ws.ChatHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatCheck {

    @Scheduled(fixedRate = 30000)
    public void heartbeatCheck() {
        MemorandumHandler.heartbeat();
        ChatHandler.heartbeat();
    }
}
