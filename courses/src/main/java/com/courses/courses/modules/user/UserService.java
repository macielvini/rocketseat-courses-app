package com.courses.courses.modules.user;

import com.courses.courses.modules.user.dto.CreateUserResponseDto;
import com.courses.courses.modules.user.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreateUserResponseDto create(UserEntity newUser) throws UserAlreadyExistsException {

        this.userRepository.findByEmail(newUser.getEmail()).ifPresent((user) -> {
            throw new UserAlreadyExistsException();
        });

        var encryptedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);

        var user = this.userRepository.save(newUser);
        return CreateUserResponseDto.builder().email(user.getEmail()).id(user.getId()).build();

    }
}
