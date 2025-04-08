package com.zanolli.backend.modules.aula.controller;

import com.zanolli.backend.modules.aula.dto.AulaCreateRequestDto;
import com.zanolli.backend.modules.aula.dto.AulaDto;
import com.zanolli.backend.modules.aula.dto.AulaFeedResponseDto;
import com.zanolli.backend.modules.aula.dto.AulaResponseDto;
import com.zanolli.backend.modules.aula.entity.AulaEntity;
import com.zanolli.backend.modules.aula.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/aula")
public class AulaController {

    private final AulaService aulaService;

    AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_PROFESSOR')")
    public ResponseEntity<AulaResponseDto> createAulaController(@RequestBody @Valid AulaCreateRequestDto aulaCreateRequestDto, JwtAuthenticationToken jwt) {
        AulaEntity saved = aulaService.createAulaService(aulaCreateRequestDto, jwt);
        String message = saved.getName() + " cadastrada com sucesso.";
        return ResponseEntity.status(HttpStatus.CREATED).body(new AulaResponseDto(message));
    }

    @GetMapping("/feed")
    @PreAuthorize("hasAuthority('SCOPE_ALUNO') or hasAuthority('SCOPE_PROFESSOR')")
    public ResponseEntity<AulaFeedResponseDto> feedController(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
            ) {
        AulaFeedResponseDto aulaFeedResponseDto = aulaService.feedService(page, pageSize);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(aulaFeedResponseDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ALUNO') or hasAuthority('SCOPE_PROFESSOR')")
    public ResponseEntity<AulaDto> findAulaByIdController(@PathVariable("id") UUID id) {
        AulaDto aulaDto = aulaService.findAulaByIdService(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(aulaDto);
    }
}
