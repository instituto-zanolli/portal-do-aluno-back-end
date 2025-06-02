package com.zanolli.backend.modules.user.service;

import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import com.zanolli.backend.modules.naipe.repository.NaipeRepository;
import com.zanolli.backend.modules.user.dto.CardResponseDto;
import com.zanolli.backend.modules.user.dto.UserCreateRequestDto;
import com.zanolli.backend.modules.user.dto.UserResponseDto;
import com.zanolli.backend.modules.user.entities.RoleEntity;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.repositories.RoleRepository;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import com.zanolli.backend.shared.exceptions.EmailConflictException;
import com.zanolli.backend.shared.exceptions.ImgNullException;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final NaipeRepository naipeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, NaipeRepository naipeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.naipeRepository = naipeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Value("${img.dir}")
    private String DIR;
    
    public UserEntity createUserService(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        Optional<UserEntity> validationEmail = userRepository.findByEmail(userCreateRequestDto.email());
        
        Optional<NaipeEntity> naipeEntity = naipeRepository.findById(userCreateRequestDto.naipeId().longValue());
        
        RoleEntity roleAluno = roleRepository.findByDescription(RoleEntity.Values.ALUNO.name()).orElseThrow(() -> new RuntimeException("Role não encontrada."));

        if(validationEmail.isPresent()) {
            throw new EmailConflictException("Por favor, tente outro email.");
        }

        UserEntity user = new UserEntity();
        user.setName(userCreateRequestDto.name());
        user.setNaipeEntity(naipeEntity.get());
        user.setDataNascimento(userCreateRequestDto.dataNascimento());
        user.setEmail(userCreateRequestDto.email());
        user.setPassword(bCryptPasswordEncoder.encode(userCreateRequestDto.password()));
        user.setRole(Set.of(roleAluno));

        return userRepository.save(user);
    }

    public UserEntity uploadImgProfileService(MultipartFile file, JwtAuthenticationToken jwt) {
        if(file.isEmpty()) {
            throw new ImgNullException("Imagem não encontrada.");
        }

        try {
            int number = new Random().nextInt(10000);

            File dir = new File(this.DIR);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = this.DIR + number + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            Optional<UserEntity> user = userRepository.findById(UUID.fromString(jwt.getName()));

            String imageProfileUrl = user.get().getImageProfileUrl();
            if (imageProfileUrl != null && !imageProfileUrl.isEmpty()) {
                Files.deleteIfExists(Path.of(imageProfileUrl));
            }

            UserEntity userEntity = user.get();
            userEntity.setImageProfileUrl(filePath);
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CardResponseDto card(JwtAuthenticationToken jwt) {
        Optional<UserEntity> user = userRepository.findById(UUID.fromString(jwt.getName()));

        if(user.get().getImageProfileUrl().isEmpty()) {
            throw new ImgNullException("Atenção: é necessário cadastrar uma foto de perfil.");
        }

        CardResponseDto responseDto = new CardResponseDto(
                user.get().getName(),
                user.get().getNaipeEntity().getDescription(),
                user.get().getDataNascimento(),
                user.get().getEmail(),
                Optional.ofNullable(user.get().getPeso()),
                user.get().getImageProfileUrl()
        );

        return responseDto;
    }
}
