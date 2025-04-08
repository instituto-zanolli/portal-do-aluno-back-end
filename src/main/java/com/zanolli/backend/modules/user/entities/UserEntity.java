package com.zanolli.backend.modules.user.entities;

import com.zanolli.backend.modules.auth.dto.AuthRequestDto;
import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuario")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID userId;
    
    private String name;

    @ManyToOne
    @JoinColumn(name = "naipe_id")
    private NaipeEntity naipeEntity;

    @Column(name = "data_nascimento")
    private Date dataNascimento;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @Column(nullable = true)
    private Integer peso;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"), // id usuário
            inverseJoinColumns = @JoinColumn(name = "role_id") // id role 
    )
    private Set<RoleEntity> role;
    
    @CreationTimestamp
    private Instant created_at;

    @UpdateTimestamp
    private LocalDate updated_at;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NaipeEntity getNaipe() {
        return naipeEntity;
    }

    public void setNaipe(NaipeEntity naipe) {
        this.naipeEntity = naipe;
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

    public boolean isLoginCorrect(@Valid AuthRequestDto authRequestDto, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(authRequestDto.password(), this.password);
    }
}
