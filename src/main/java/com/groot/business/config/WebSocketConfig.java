package com.groot.business.config;

import com.groot.business.ws.ChatHandler;
import com.groot.business.ws.MemorandumHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MemorandumHandler memorandumHandler;

    private final ChatHandler chatHandler;

    private final WebSocketInterceptor webSocketInterceptor;

    public WebSocketConfig(final MemorandumHandler memorandumHandler, final WebSocketInterceptor webSocketInterceptor, final ChatHandler chatHandler) {
        this.memorandumHandler = memorandumHandler;
        this.chatHandler = chatHandler;
        this.webSocketInterceptor = webSocketInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(memorandumHandler, "/ws/memorandum")
                .addHandler(chatHandler, "/ws/chat")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }
}
