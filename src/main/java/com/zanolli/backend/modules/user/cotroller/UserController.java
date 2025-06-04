package com.zanolli.backend.modules.user.cotroller;

import com.zanolli.backend.modules.aula.dtos.aula.AulaDto;
import com.zanolli.backend.modules.user.dto.CardResponseDto;
import com.zanolli.backend.modules.user.dto.UserCreateRequestDto;
import com.zanolli.backend.modules.user.dto.UserProfileDto;
import com.zanolli.backend.modules.user.dto.UserResponseDto;
import com.zanolli.backend.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping()
    public ResponseEntity<UserResponseDto> createUserController(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        userService.createUserService(userCreateRequestDto);
        String message = "usuário cadastrado com sucesso!";
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(message));
    }

    @PostMapping("/profile/settings")
    public  ResponseEntity<UserResponseDto> uploadImgProfileController(@RequestParam("file") MultipartFile file, JwtAuthenticationToken jwt) {
        userService.uploadImgProfileService(file, jwt);
        String message = "imagem inserida com sucesso!";
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(message));
    }

    @GetMapping("/profile/card")
    public ResponseEntity<CardResponseDto> cardController(JwtAuthenticationToken jwt) {
        CardResponseDto card = userService.cardService(jwt);
        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> profileController(UserProfileDto userProfileDto, JwtAuthenticationToken jwt) {
        UserProfileDto userProfile = userService.profileService(userProfileDto, jwt);
        return ResponseEntity.status(HttpStatus.OK).body(userProfile);
    }
}
