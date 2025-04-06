package com.zanolli.backend.modules.naipe.repository;

import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NaipeRepository extends JpaRepository<NaipeEntity, Integer> {
    Optional<NaipeEntity> findByDescription(String description);
}
