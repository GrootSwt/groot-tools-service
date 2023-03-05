package com.chat.business.config;

import com.chat.business.ws.MemorandumHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MemorandumHandler memorandumHandler;

    private final WebSocketInterceptor webSocketInterceptor;

    @Autowired
    public WebSocketConfig(MemorandumHandler memorandumHandler, WebSocketInterceptor webSocketInterceptor) {
        this.memorandumHandler = memorandumHandler;
        this.webSocketInterceptor = webSocketInterceptor;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(memorandumHandler, "/ws/memorandum")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }
}
