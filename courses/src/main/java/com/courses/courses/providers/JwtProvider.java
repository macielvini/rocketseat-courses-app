package com.courses.courses.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

    @Value("${security.token.secret}")
    private String secret;

    public String validateBearerToken(String token) {
        token = token.replace("Bearer ", "");
        var algorithm = Algorithm.HMAC256(secret);
        try {
            return JWT.require(algorithm).build().verify(token).getSubject();

        } catch (JWTVerificationException e) {
            return "";
        }
    }
}
