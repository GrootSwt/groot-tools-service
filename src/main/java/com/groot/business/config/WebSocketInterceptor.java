package com.groot.business.config;

import com.groot.business.utils.JWTUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        List<String> protocolList = request.getHeaders().get("Sec-WebSocket-Protocol");
        if (null != protocolList && !protocolList.isEmpty()) {
            String token = protocolList.get(0);
            JWTUtil.verifyToken(token);
            response.getHeaders().add("Sec-WebSocket-Protocol", protocolList.get(0));
            return  true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
