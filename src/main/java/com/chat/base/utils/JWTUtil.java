package com.chat.base.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chat.base.exception.BusinessRuntimeException;

public class JWTUtil {

    public static String getToken(String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("chat");
            return JWT.create()
                    .withClaim("username", username)
                    .sign(algorithm);
        }catch (JWTCreationException e) {
            e.printStackTrace();
            throw new BusinessRuntimeException("token生成失败", 410);
        }
    }

    public static String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("chat");
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim username = decodedJWT.getClaim("username");
            return username.asString();
        }catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new BusinessRuntimeException("token验证失败", 410);
        }
    }
}
