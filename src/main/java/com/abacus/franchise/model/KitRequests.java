package com.abacus.franchise.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.abacus.franchise.enums.RequestStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "kit_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitRequests {

   @Id
   @UuidGenerator
   @JdbcTypeCode(SqlTypes.VARCHAR)
   @Column(name = "kit_request_id", length = 36, updatable = false, nullable = false)
   private UUID kitRequestId;

   @JdbcTypeCode(SqlTypes.VARCHAR)
   @Column(name = "franchise_id", length = 36, updatable = false, nullable = false)
   private UUID franchiseId;

   @Builder.Default
   @Enumerated(EnumType.STRING)
   @Column(name = "request_status", nullable = false)
   private RequestStatus requestStatus = RequestStatus.PLACED;

   @JdbcTypeCode(SqlTypes.VARCHAR)
   @Column(name = "address_id", length = 36, updatable = false, nullable = false)
   private UUID addressId;

   @Column(name = "placed_date")
   private LocalDateTime placedDate;

   @Column(name = "dispatched_date")
   private LocalDateTime dispatchedDate;

   @Column(name = "delivered_date")
   private LocalDateTime deliveredDate;

   @Builder.Default
   @Column(name = "is_active")
   private Boolean isActive = true;

   @Column(name = "created_at", updatable = false)
   private LocalDateTime createdAt;

   @Column(name = "updated_at")
   private LocalDateTime updatedAt;

   @JdbcTypeCode(SqlTypes.VARCHAR)
   @Column(name = "created_by", length = 36)
   private UUID createdBy;

   @JdbcTypeCode(SqlTypes.VARCHAR)
   @Column(name = "updated_by", length = 36)
   private UUID updatedBy;

   @PrePersist
   protected void onCreate() {
      this.createdAt = LocalDateTime.now();
      this.updatedAt = LocalDateTime.now();
      this.placedDate = LocalDateTime.now();
   }

   @PreUpdate
   protected void onUpdate() {
      this.updatedAt = LocalDateTime.now();
   }
}