package com.zanolli.backend.modules.user.service;

import com.zanolli.backend.modules.aula.dtos.aula.AulaDto;
import com.zanolli.backend.modules.aula.entities.AulaEntity;
import com.zanolli.backend.modules.aula.entities.InscricaoEntity;
import com.zanolli.backend.modules.aula.repositories.InscricaoRepository;
import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import com.zanolli.backend.modules.naipe.repository.NaipeRepository;
import com.zanolli.backend.modules.user.dto.CardResponseDto;
import com.zanolli.backend.modules.user.dto.UserCreateRequestDto;
import com.zanolli.backend.modules.user.dto.UserDto;
import com.zanolli.backend.modules.user.dto.UserProfileDto;
import com.zanolli.backend.modules.user.entities.RoleEntity;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.repositories.RoleRepository;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import com.zanolli.backend.shared.exceptions.EmailConflictException;
import com.zanolli.backend.shared.exceptions.ImgNullException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final NaipeRepository naipeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final InscricaoRepository inscricaoRepository;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, NaipeRepository naipeRepository, BCryptPasswordEncoder bCryptPasswordEncoder, InscricaoRepository inscricaoRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.naipeRepository = naipeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.inscricaoRepository = inscricaoRepository;
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

    public CardResponseDto cardService(JwtAuthenticationToken jwt) {
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

    public UserProfileDto profileService(UserProfileDto userProfileDto, JwtAuthenticationToken jwt) {
        Optional<UserEntity> user = userRepository.findById(UUID.fromString(jwt.getName()));

        UserDto userDto = new UserDto(
                user.get().getUserId(),
                user.get().getName(),
                Optional.ofNullable(user.get().getImageProfileUrl()),
                user.get().getNaipeEntity(),
                user.get().getDataNascimento(),
                user.get().getEmail(),
                Optional.ofNullable(user.get().getPeso()),
                user.get().getRole().stream().findFirst().orElse(null)
        );

        List<InscricaoEntity> inscricaoEntities = inscricaoRepository.findAllByUserId(user.get().getUserId());

        List<AulaDto> aulaDtos = inscricaoEntities.stream()
                .map(inscricao -> {
                    AulaEntity aula = inscricao.getAulaEntity();
                    return new AulaDto(
                            aula.getName(),
                            aula.getDescription(),
                            aula.getEstilo(),
                            aula.getDate(),
                            aula.getStartTime(),
                            aula.getEndTime()
                    );
                }).toList();

        return new UserProfileDto(userDto, aulaDtos);
    }
}
