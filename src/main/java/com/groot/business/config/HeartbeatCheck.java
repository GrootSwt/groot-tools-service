package com.groot.business.config;

import com.groot.business.ws.MemorandumHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatCheck {

    @Scheduled(fixedRate = 30000)
    public void heartbeatCheck() {
        MemorandumHandler.heartbeat();
    }
}
