package com.zanolli.backend.modules.aula.controller;

import com.zanolli.backend.modules.aula.dto.AulaCreateRequestDto;
import com.zanolli.backend.modules.aula.dto.AulaResponseDto;
import com.zanolli.backend.modules.aula.entity.AulaEntity;
import com.zanolli.backend.modules.aula.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aula")
public class AulaController {

    private final AulaService aulaService;

    AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping()
    public ResponseEntity<AulaResponseDto> createAulaController(@RequestBody @Valid AulaCreateRequestDto aulaCreateRequestDto) {
        AulaEntity saved = aulaService.createAulaService(aulaCreateRequestDto);
        String message = saved.getName() + " cadastrada com sucesso.";
        return ResponseEntity.status(HttpStatus.CREATED).body(new AulaResponseDto(message));
    }
}
