package com.zanolli.backend.modules.evento.service;

import com.zanolli.backend.modules.evento.dto.EventoCreateRequestDto;
import com.zanolli.backend.modules.evento.entity.EventoEntity;
import com.zanolli.backend.modules.evento.repository.EventoRepository;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import com.zanolli.backend.shared.exceptions.ImgNullException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class EventoService {

    private final UserRepository userRepository;
    private final EventoRepository eventoRepository;

    public EventoService (UserRepository userRepository, EventoRepository eventoRepository) {
        this.userRepository = userRepository;
        this.eventoRepository = eventoRepository;
    }

    @Value("${img.dir}")
    private String DIR;

    public EventoEntity createEventoService(EventoCreateRequestDto eventoCreateRequestDto, JwtAuthenticationToken jwt) {
        UserEntity user = userRepository.findById(UUID.fromString(jwt.getName()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        EventoEntity evento = new EventoEntity();
        evento.setTitle(eventoCreateRequestDto.title());
        evento.setDescription(eventoCreateRequestDto.description());
        evento.setDate(eventoCreateRequestDto.date());
        evento.setProfessor(user);

        return eventoRepository.save(evento);
    }

    public EventoEntity uploadImgEventoService(Long eventoId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImgNullException("imagem não encontrada.");
        }

        try {
            int number = new Random().nextInt(10000);

            File dir = new File(this.DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = this.DIR + number + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);
            if (eventoEntity.isEmpty()) {
                throw new RuntimeException("evento não encontrado.");
            }

            EventoEntity evento = eventoEntity.get();

            String imgEventoUrl = evento.getImgEventoUrl();
            if (imgEventoUrl != null && !imgEventoUrl.isEmpty()) {
                Files.deleteIfExists(Path.of(imgEventoUrl));
            }

            evento.setImgEventoUrl(filePath);
            return eventoRepository.save(evento);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
