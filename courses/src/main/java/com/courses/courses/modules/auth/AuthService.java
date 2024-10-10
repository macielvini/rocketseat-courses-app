package com.courses.courses.modules.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.courses.courses.modules.auth.dto.LoginDto;
import com.courses.courses.modules.auth.exceptions.UserNotFoundException;
import com.courses.courses.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret}")
    private String secret;

    public String login(LoginDto loginDto) throws AuthenticationException {
        var user = this.userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        var passwordMatch = this.passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

        if (!passwordMatch) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withSubject(user.getId().toString()).withIssuer("Rocket Courses").sign(algorithm);
    }
}
