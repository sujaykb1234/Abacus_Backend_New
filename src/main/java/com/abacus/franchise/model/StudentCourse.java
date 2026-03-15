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
@Table(name = "student_course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourse {

	@Id
	@UuidGenerator
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "student_course_id", length = 36, updatable = false, nullable = false)
	private UUID studentCourseId;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "student_id", length = 36, updatable = false, nullable = false)
	private UUID studentId;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "course_id", length = 36, updatable = false, nullable = false)
	private UUID courseId;
	
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "franchise_id", length = 36, updatable = false, nullable = false)
	private UUID franchiseId;

	@Column(name = "assign_date")
	private LocalDateTime assignDate;

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

	@Builder.Default
	@Column(name = "course_active", nullable = false)
	private Boolean courseActive = true;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.assignDate = LocalDateTime.now();

	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
