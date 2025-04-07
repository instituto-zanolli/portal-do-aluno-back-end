package com.zanolli.backend.modules.aula.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "aula")
public class AulaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // precisa ter um estilo antes
}
