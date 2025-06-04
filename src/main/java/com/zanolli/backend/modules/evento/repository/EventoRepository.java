package com.zanolli.backend.modules.evento.repository;

import com.zanolli.backend.modules.evento.entity.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoRepository extends JpaRepository<EventoEntity, UUID> {
}
