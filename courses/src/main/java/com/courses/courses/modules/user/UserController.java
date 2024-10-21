package com.courses.courses.modules.user;

import com.courses.courses.modules.user.dto.CreateUserBodyDto;
import com.courses.courses.modules.user.dto.CreateUserResponseDto;
import com.courses.courses.modules.user.exceptions.UserAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "User", description = "User related requests")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Create an user")
    @PostMapping("/")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CreateUserResponseDto.class))
            })
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreateUserBodyDto user) {
        try {
            var userEntity = UserEntity.builder().email(user.getEmail()).password(user.getPassword()).build();
            var result = this.userService.create(userEntity);

            return ResponseEntity.ok(result);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
