package com.zanolli.backend.modules.user.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id")
    private Long roleId;
    
    private String description;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public enum Values {
        aluno(1L),
        professor(2L);
        
        long roleId;
        
        Values(long roleId) {
            this.roleId = roleId;
        }

        public long getId() {
            return roleId;
        }
    }
}
