package com.groot.business.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.groot.base.exception.BusinessRuntimeException;
import com.groot.base.exception.WSRuntimeException;
import com.groot.business.model.User;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

public class JWTUtil {

    public static String getToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("memorandum");
            return JWT.create()
                    .withClaim("account", user.getAccount())
                    .withClaim("id", user.getId())
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        }catch (JWTCreationException e) {
            e.printStackTrace();
            throw new BusinessRuntimeException("token生成失败", 401);
        }
    }

    public static User verifyToken(String token, WebSocketSession session) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("memorandum");
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim account = decodedJWT.getClaim("account");
            Claim id = decodedJWT.getClaim("id");
            User user = new User();
            user.setId(id.asString());
            user.setAccount(account.asString());
            return user;
        }catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new WSRuntimeException(401, session, "token验证失败");
        }
    }

    public static User verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("memorandum");
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim account = decodedJWT.getClaim("account");
            Claim id = decodedJWT.getClaim("id");
            User user = new User();
            user.setId(id.asString());
            user.setAccount(account.asString());
            return user;
        }catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new BusinessRuntimeException("token验证失败", 401);
        }
    }
}
