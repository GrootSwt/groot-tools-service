package com.groot.business.config;

import com.groot.business.ws.Chat;
import com.groot.business.ws.MemorandumWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MemorandumWebSocketHandler memorandumWebSocketHandler;

    private final Chat chat;

    private final WebSocketInterceptor webSocketInterceptor;

    public WebSocketConfig(final MemorandumWebSocketHandler memorandumWebSocketHandler, final WebSocketInterceptor webSocketInterceptor, final Chat chat) {
        this.memorandumWebSocketHandler = memorandumWebSocketHandler;
        this.chat = chat;
        this.webSocketInterceptor = webSocketInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(memorandumWebSocketHandler, "/ws/memorandum")
                .addHandler(chat, "/ws/chat")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }
}
