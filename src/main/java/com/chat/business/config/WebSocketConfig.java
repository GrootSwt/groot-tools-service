package com.chat.business.config;

import com.chat.business.ws.ChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler;

    private final WebSocketInterceptor webSocketInterceptor;

    @Autowired
    public WebSocketConfig(ChatWebSocketHandler chatWebSocketHandler, WebSocketInterceptor webSocketInterceptor) {
        this.chatWebSocketHandler = chatWebSocketHandler;
        this.webSocketInterceptor = webSocketInterceptor;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }
}
