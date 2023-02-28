package com.chat.base.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.socket.WebSocketSession;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessWebSocketException extends RuntimeException{

    private int code = 400;

    private WebSocketSession session;

    public BusinessWebSocketException(WebSocketSession session) {
        super();
        this.session = session;
    }

    public BusinessWebSocketException(WebSocketSession session, String message) {
        super(message);
        this.session = session;
    }

    public BusinessWebSocketException(WebSocketSession session, String message, int code) {
        super(message);
        this.session = session;
        this.code = code;
    }

}
