package com.courses.courses.modules.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.courses.courses.modules.auth.dto.LoginDto;
import com.courses.courses.modules.auth.dto.LoginResponseDto;
import com.courses.courses.modules.auth.exceptions.UserNotFoundException;
import com.courses.courses.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret}")
    private String secret;

    public LoginResponseDto login(LoginDto loginDto) throws AuthenticationException {
        var user = this.userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        var passwordMatch = this.passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

        if (!passwordMatch) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresIn = Instant.now().plus(Duration.ofHours(24));
        var token = JWT
                .create()
                .withSubject(user.getId().toString())
                .withClaim("roles", List.of("USER"))
                .withIssuer("Rocket Courses")
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        return LoginResponseDto.builder().token(token).expires_in(expiresIn.toEpochMilli()).build();
    }
}
