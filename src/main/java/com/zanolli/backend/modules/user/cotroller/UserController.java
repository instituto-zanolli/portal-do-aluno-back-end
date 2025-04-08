package com.zanolli.backend.modules.user.cotroller;

import com.zanolli.backend.modules.user.dto.UserCreateRequestDto;
import com.zanolli.backend.modules.user.dto.UserResponseDto;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping()
    public ResponseEntity<UserResponseDto> createUserController(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        UserEntity saved = userService.createUserService(userCreateRequestDto);
        String message = "usuário cadastrado com sucesso!";
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(message));
    }
}
