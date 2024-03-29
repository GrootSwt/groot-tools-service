package com.groot.business.utils;

import com.groot.business.exception.WSRuntimeException;
import com.groot.business.bean.enums.MemorandumOperationType;
import com.groot.business.model.User;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class WSUtil {

    public static User getUserFromProtocols(WebSocketSession session) {
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> protocols = handshakeHeaders.get("Sec-WebSocket-Protocol");
        if (null != protocols && !protocols.isEmpty()) {
            Object loginId = StpUtil.getLoginIdByToken(protocols.get(0));
            if (null == loginId) {
                throw new WSRuntimeException(401, session, "用户信息校验失败");
            }

            String id = (String) loginId;
            String account = (String) StpUtil.getExtra(protocols.get(0), "account");
            String displayName = (String) StpUtil.getExtra(protocols.get(0), "displayName");
            User user = new User();
            user.setId(id);
            user.setAccount(account);
            user.setDisplayName(displayName);
            if (StringUtils.hasText(user.getId()) && StringUtils.hasText(user.getAccount())) {
                return user;
            }
        }
        throw new WSRuntimeException(401, session, "用户信息校验失败");
    }

    public static void heartbeat(CopyOnWriteArraySet<WebSocketSession> sessions, AtomicInteger sessionNumber,
            String wsName) {
        if (!sessions.isEmpty()) {
            Iterator<WebSocketSession> iterator = sessions.iterator();
            log.info(wsName + "心跳检测前连接数量：" + sessions.size());
            while (iterator.hasNext()) {
                try {
                    iterator.next().sendMessage(new TextMessage(MemorandumOperationType.HEARTBEAT.getDesc()));
                } catch (IOException e) {
                    iterator.remove();
                }
            }
            sessionNumber.set(sessions.size());
            log.info(wsName + "心跳检测后连接数量：" + sessions.size());
        }
    }
}
