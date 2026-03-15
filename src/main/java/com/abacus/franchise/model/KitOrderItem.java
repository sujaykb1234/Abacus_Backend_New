package com.abacus.franchise.model;

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
@Table(name = "kit_order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitOrderItem {

	@Id
	@UuidGenerator
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "order_item_id", length = 36, updatable = false, nullable = false)
	private UUID orderItemId;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "course_id", length = 36)
    private UUID courseId;

    @Column(name = "kit_count")
    private int kitCount;
    
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "kit_request_id", length = 36, updatable = false, nullable = false)
    private UUID kitRequestId;

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
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
	
}

