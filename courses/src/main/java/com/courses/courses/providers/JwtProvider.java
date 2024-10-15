package com.courses.courses.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

    @Value("${security.token.secret}")
    private String secret;

    public DecodedJWT validateBearerToken(String token) {
        token = token.replace("Bearer ", "");
        var algorithm = Algorithm.HMAC256(secret);
        try {
            return JWT.require(algorithm).build().verify(token);

        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
