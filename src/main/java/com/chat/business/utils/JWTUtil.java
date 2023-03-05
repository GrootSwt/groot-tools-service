package com.chat.business.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chat.base.exception.BusinessRuntimeException;
import com.chat.base.exception.WSRuntimeException;
import com.chat.business.model.User;
import org.springframework.web.socket.WebSocketSession;

public class JWTUtil {

    public static String getToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("chat");
            return JWT.create()
                    .withClaim("username", user.getUsername())
                    .withClaim("id", user.getId())
                    .sign(algorithm);
        }catch (JWTCreationException e) {
            e.printStackTrace();
            throw new BusinessRuntimeException("token生成失败", 401);
        }
    }

    public static User verifyToken(String token, boolean isWS, WebSocketSession session) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("chat");
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim username = decodedJWT.getClaim("username");
            Claim id = decodedJWT.getClaim("id");
            User user = new User();
            user.setId(id.asString());
            user.setUsername(username.asString());
            return user;
        }catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new WSRuntimeException(401, session, "token验证失败");
        }
    }

    public static User verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("chat");
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim username = decodedJWT.getClaim("username");
            Claim id = decodedJWT.getClaim("id");
            User user = new User();
            user.setId(id.asString());
            user.setUsername(username.asString());
            return user;
        }catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new BusinessRuntimeException("token验证失败", 401);
        }
    }
}
