package com.zanolli.backend.modules.aula.service;

import com.zanolli.backend.modules.aula.dtos.aula.AulaCreateRequestDto;
import com.zanolli.backend.modules.aula.dtos.aula.AulaRepresentationDto;
import com.zanolli.backend.modules.aula.dtos.aula.AulaFeedResponseDto;
import com.zanolli.backend.modules.aula.entities.AulaEntity;
import com.zanolli.backend.modules.aula.entities.InscricaoEntity;
import com.zanolli.backend.modules.aula.repositories.AulaRepository;
import com.zanolli.backend.modules.aula.repositories.InscricaoRepository;
import com.zanolli.backend.modules.estilo.entity.EstiloEntity;
import com.zanolli.backend.modules.estilo.repository.EstiloRepository;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import com.zanolli.backend.shared.exceptions.AulaNotFoundException;
import com.zanolli.backend.shared.exceptions.EstiloConflictException;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AulaService {
    
    private final AulaRepository aulaRepository;
    private final EstiloRepository estiloRepository;
    private final UserRepository userRepository;
    private final InscricaoRepository inscricaoRepository;
    
    public AulaService (AulaRepository aulaRepository, EstiloRepository estiloRepository, UserRepository userRepository, InscricaoRepository inscricaoRepository) {
        this.aulaRepository = aulaRepository;
        this.estiloRepository = estiloRepository;
        this.userRepository = userRepository;
        this.inscricaoRepository = inscricaoRepository;
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

        List<AulaRepresentationDto> aulaRepresentationDtoList = aulas
                .stream()
                .map(aulaEntity -> new AulaRepresentationDto(
                        aulaEntity.getAulaId(),
                        aulaEntity.getName(),
                        aulaEntity.getDescription(),
                        aulaEntity.getEstilo().getDescription(),
                        aulaEntity.getDate(),
                        aulaEntity.getStartTime(),
                        aulaEntity.getEndTime()
                ))
                .collect(Collectors.toList());

        return new AulaFeedResponseDto(aulaRepresentationDtoList, page, pageSize);
    }

    public AulaRepresentationDto findAulaByIdService(UUID id) {
        Optional<AulaEntity> aulaEntity = aulaRepository.findById(id);

        if(aulaEntity.isEmpty()) {
            System.out.println("Aula não encontrada");
        }

        AulaRepresentationDto aulaRepresentationDto = new AulaRepresentationDto(
                aulaEntity.get().getAulaId(),
                aulaEntity.get().getName(),
                aulaEntity.get().getDescription(),
                aulaEntity.get().getEstilo().getDescription(),
                aulaEntity.get().getDate(),
                aulaEntity.get().getStartTime(),
                aulaEntity.get().getEndTime()
        );

        return aulaRepresentationDto;
    }

    public InscricaoEntity inscreverAlunoAulaService(UUID id, JwtAuthenticationToken jwt) {
        Optional<UserEntity> userEntity = userRepository.findById(UUID.fromString(jwt.getName()));
        Optional<AulaEntity> aulaEntity = aulaRepository.findById(id);
        
        if(userEntity.isEmpty() || aulaEntity.isEmpty()) {
            throw new IllegalStateException("Não foi possível realizar inscrição.");
        }

        if(inscricaoRepository.existsByAulaEntityAndUserEntity(aulaEntity.get(), userEntity.get())) {
            throw new IllegalStateException("Aluno já matriculado na aula.");
        }

        InscricaoEntity inscricaoEntity = new InscricaoEntity();
        inscricaoEntity.setUserEntity(userEntity.get());
        inscricaoEntity.setAulaEntity(aulaEntity.get());
        
        return inscricaoRepository.save(inscricaoEntity);
    }

    public void deleteAulaService(UUID id, JwtAuthenticationToken jwt) {

        Optional<AulaEntity> aula = aulaRepository.findById(id);

        if(aula.isEmpty()) {
            throw new AulaNotFoundException("Atenção: essa aula não existe.");
        }

        inscricaoRepository.deleteByAulaId(id);
        aulaRepository.deleteById(id);
    }
}
