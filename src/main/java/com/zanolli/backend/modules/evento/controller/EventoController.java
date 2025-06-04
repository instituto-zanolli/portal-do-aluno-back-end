package com.zanolli.backend.modules.evento.controller;

import com.zanolli.backend.modules.evento.dto.EventoCreateRequestDto;
import com.zanolli.backend.modules.evento.entity.EventoEntity;
import com.zanolli.backend.modules.evento.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;

    EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_PROFESSOR')")
    public ResponseEntity<String> createEventoController(
            @RequestPart("file") MultipartFile file,
            @RequestPart("evento") @Valid EventoCreateRequestDto eventoCreateRequestDto,
            JwtAuthenticationToken jwt) {

        EventoEntity evento = eventoService.createEventoService(file, eventoCreateRequestDto, jwt);
        String message = "Evento cadastrado com sucesso.";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}
