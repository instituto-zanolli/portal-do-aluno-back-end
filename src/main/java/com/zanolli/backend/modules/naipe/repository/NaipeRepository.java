package com.zanolli.backend.modules.naipe.repository;

import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NaipeRepository extends JpaRepository<NaipeEntity, Integer> {
    Optional<NaipeEntity> findByDescription(String description);
}
