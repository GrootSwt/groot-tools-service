package com.chat.base.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.socket.WebSocketSession;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatRuntimeException extends RuntimeException {

    private int code = 400;

    private WebSocketSession session;

    public ChatRuntimeException(WebSocketSession session) {
        super();
        this.session = session;
    }

    public ChatRuntimeException(WebSocketSession session, String message) {
        super(message);
        this.session = session;
    }

    public ChatRuntimeException(int code, WebSocketSession session, String message) {
        super(message);
        this.session = session;
        this.code = code;
    }

}
