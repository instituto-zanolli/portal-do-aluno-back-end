package com.zanolli.backend.modules.evento.entity;

import com.zanolli.backend.modules.user.entities.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "evento")
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long eventoId;

    private String title;

    private String description;

    private Date date;

    @Column(nullable = true)
    private String imgEventoUrl;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private UserEntity professor;

    @CreationTimestamp
    private Instant created_at;

    @UpdateTimestamp
    private LocalDate updated_at;

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImgEventoUrl() {
        return imgEventoUrl;
    }

    public void setImgEventoUrl(String imgEventoUrl) {
        this.imgEventoUrl = imgEventoUrl;
    }

    public UserEntity getProfessor() {
        return professor;
    }

    public void setProfessor(UserEntity professor) {
        this.professor = professor;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }
}
