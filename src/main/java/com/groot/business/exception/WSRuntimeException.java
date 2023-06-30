package com.groot.business.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.socket.WebSocketSession;

@EqualsAndHashCode(callSuper = true)
@Data
public class WSRuntimeException extends RuntimeException {

    private int code = 400;

    private WebSocketSession session;

    public WSRuntimeException(WebSocketSession session) {
        super();
        this.session = session;
    }

    public WSRuntimeException(WebSocketSession session, String message) {
        super(message);
        this.session = session;
    }

    public WSRuntimeException(int code, WebSocketSession session, String message) {
        super(message);
        this.session = session;
        this.code = code;
    }

}
