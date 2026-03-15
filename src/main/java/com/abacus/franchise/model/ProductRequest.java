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
@Table(name = "product_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
	
	@Id
	@UuidGenerator
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "product_request_id", length = 36, updatable = false, nullable = false)
	private UUID productRequestId;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "franchise_id", length = 36, updatable = false, nullable = false)
	private UUID franchiseId;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "product_id", length = 36, updatable = false, nullable = false)
	private UUID productId;
	
	@Column(name = "quantity")
	private int quantity;

	@Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;
	
	@Column(name = "request_date")
	private LocalDateTime requestDate;
	
	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "request_status", nullable = false)
	private RequestStatus requestStatus = RequestStatus.PLACED;

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
		this.requestDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

}
