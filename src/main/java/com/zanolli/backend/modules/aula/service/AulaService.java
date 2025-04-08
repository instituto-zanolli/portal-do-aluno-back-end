package com.zanolli.backend.modules.aula.service;

import com.zanolli.backend.modules.aula.dto.AulaCreateRequestDto;
import com.zanolli.backend.modules.aula.dto.AulaDto;
import com.zanolli.backend.modules.aula.dto.AulaFeedResponseDto;
import com.zanolli.backend.modules.aula.dto.AulaResponseDto;
import com.zanolli.backend.modules.aula.entity.AulaEntity;
import com.zanolli.backend.modules.aula.repository.AulaRepository;
import com.zanolli.backend.modules.estilo.entity.EstiloEntity;
import com.zanolli.backend.modules.estilo.repository.EstiloRepository;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import com.zanolli.backend.shared.exceptions.EstiloConflictException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AulaService {
    
    private final AulaRepository aulaRepository;
    private final EstiloRepository estiloRepository;
    private final UserRepository userRepository;
    
    public AulaService (AulaRepository aulaRepository, EstiloRepository estiloRepository, UserRepository userRepository) {
        this.aulaRepository = aulaRepository;
        this.estiloRepository = estiloRepository;
        this.userRepository = userRepository;
    }
    
    public AulaEntity createAulaService(@RequestBody @Valid AulaCreateRequestDto aulaCreateRequestDto, JwtAuthenticationToken jwt) {
        String name = aulaCreateRequestDto.name() + " | " + aulaCreateRequestDto.startTime();

        Optional<EstiloEntity> estiloEntity = estiloRepository.findById(aulaCreateRequestDto.estiloId());
        Optional<UserEntity> userEntity = userRepository.findById(UUID.fromString(jwt.getName()));

        if(estiloEntity.isEmpty()) {
            throw new EstiloConflictException("Este estilo não existe na base de dados.");
        }

        AulaEntity aula = new AulaEntity();
        aula.setName(name);
        aula.setDescription(aulaCreateRequestDto.description());
        aula.setEstilo(estiloEntity.get());
        aula.setDate(aulaCreateRequestDto.date());
        aula.setStartTime(aulaCreateRequestDto.startTime());
        aula.setEndTime(aulaCreateRequestDto.endTime());
        aula.setProfessor(userEntity.get());

        return aulaRepository.save(aula);
    }

    public AulaFeedResponseDto feedService(int page, int pageSize) {
        var aulas = aulaRepository.findAll(PageRequest.of(page, pageSize));

        List<AulaDto> aulaDtoList = aulas
                .stream()
                .map(aulaEntity -> new AulaDto(
                        aulaEntity.getName(),
                        aulaEntity.getDescription(),
                        aulaEntity.getEstilo().getDescription(),
                        aulaEntity.getDate(),
                        aulaEntity.getStartTime(),
                        aulaEntity.getEndTime()
                ))
                .collect(Collectors.toList());

        return new AulaFeedResponseDto(aulaDtoList, page, pageSize);
    }
}
