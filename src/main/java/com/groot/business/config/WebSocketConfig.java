package com.groot.business.config;

import com.groot.business.ws.Chat;
import com.groot.business.ws.Memorandum;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final Memorandum memorandum;

    private final Chat chat;

    private final WebSocketInterceptor webSocketInterceptor;

    public WebSocketConfig(final Memorandum memorandum, final WebSocketInterceptor webSocketInterceptor, final Chat chat) {
        this.memorandum = memorandum;
        this.chat = chat;
        this.webSocketInterceptor = webSocketInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(memorandum, "/ws/memorandum")
                .addHandler(chat, "/ws/chat")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }
}
