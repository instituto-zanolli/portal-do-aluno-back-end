package com.zanolli.backend.modules.user.entities;

import com.zanolli.backend.modules.auth.dto.AuthRequest;
import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private String name;

    @ManyToOne
    @JoinColumn(name = "naipe_id")
    private NaipeEntity naipe;

    @Column(name = "data_nascimento")
    private Date dataNascimento;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @Column(nullable = true)
    private Integer peso;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"), // id usuário
            inverseJoinColumns = @JoinColumn(name = "role_id") // id role 
    )
    private Set<RoleEntity> role;
    
    @CreationTimestamp
    private Instant created_at;

    @UpdateTimestamp
    private LocalDate updated_at;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NaipeEntity getNaipe() {
        return naipe;
    }

    public void setNaipe(NaipeEntity naipe) {
        this.naipe = naipe;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Set<RoleEntity> getRole() {
        return role;
    }

    public void setRole(Set<RoleEntity> role) {
        this.role = role;
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

    public boolean isLoginCorrect(@Valid AuthRequest authRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(authRequest.password(), this.password);
    }
}
