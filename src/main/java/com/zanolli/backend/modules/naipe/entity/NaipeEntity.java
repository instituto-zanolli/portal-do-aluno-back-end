package com.zanolli.backend.modules.naipe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "naipe")
public class NaipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long naipeId;

    @Column( unique = true)
    private String description;

    public Long getNaipeId() {
        return naipeId;
    }

    public void setNaipeId(Long naipeId) {
        this.naipeId = naipeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
