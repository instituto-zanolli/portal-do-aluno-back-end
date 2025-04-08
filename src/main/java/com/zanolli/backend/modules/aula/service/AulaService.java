package com.zanolli.backend.modules.aula.service;

import com.zanolli.backend.modules.aula.dto.AulaCreateRequestDto;
import com.zanolli.backend.modules.aula.dto.AulaResponseDto;
import com.zanolli.backend.modules.aula.entity.AulaEntity;
import com.zanolli.backend.modules.aula.repository.AulaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AulaService {
    
    private final AulaRepository aulaRepository;
    
    public AulaService (AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
    }
    
    public AulaEntity createAulaService(@RequestBody @Valid AulaCreateRequestDto aulaCreateRequestDto) {
        
    }
}
