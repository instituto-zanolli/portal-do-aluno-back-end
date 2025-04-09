package com.zanolli.backend.modules.aula.repositories;

import com.zanolli.backend.modules.aula.entities.AulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AulaRepository extends JpaRepository<AulaEntity, UUID> {
}
