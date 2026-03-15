package com.abacus.franchise.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "user_id", length = 36, updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "franchise_name", length = 100)
    private String franchiseName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(unique = true, length = 15)
    private String mobile;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "role_id", length = 36, updatable = false, nullable = false)
    private UUID roleId;

    @Column(name = "profile_name", length = 100)
    private String profileName;
    
    @Column(name = "profile_link", length = 100)
    private String profileLink;
    
    @Column(name = "document_name", length = 100)
    private String documentName;
    
    @Column(name = "document_link", length = 100)
    private String documentLink;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "franchise_id", length = 36)
    private UUID franchiseId;

    @Column(name = "address_id",length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID addressId;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Builder.Default
    private Boolean isActive = true;

    @Builder.Default
    private Integer failedLoginAttempts = 0;

    private LocalDateTime lastLogin;
    private LocalDateTime accountLockedUntil;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "created_by", length = 36)
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
