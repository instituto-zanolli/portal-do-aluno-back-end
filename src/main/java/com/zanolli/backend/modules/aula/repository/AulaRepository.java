package com.zanolli.backend.modules.aula.repository;

import com.zanolli.backend.modules.aula.entity.AulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AulaRepository extends JpaRepository<AulaEntity, UUID> {
    
}
