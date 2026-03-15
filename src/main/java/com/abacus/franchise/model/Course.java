package com.abacus.franchise.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.abacus.franchise.enums.CourseType;

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
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

		@Id
		@UuidGenerator
		@JdbcTypeCode(SqlTypes.VARCHAR)
		@Column(name = "course_id", length = 36, updatable = false, nullable = false)
		private UUID courseId;
		
		@Column(name = "course_name", nullable = false, length = 150)
		private String courseName;
	
		
		@Column(name = "duration_days", nullable = false)
		private int durationDays;
		
		@Enumerated(EnumType.STRING)
		@Column(name = "course_type", nullable = false, length = 50)
		private CourseType courseType;
		
		@Column(name = "no_of_books", nullable = false)
		private int noOfBooks;
		
		@Builder.Default
		@Column(name = "is_active", nullable = false)
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
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        this.updatedAt = LocalDateTime.now();
	    }

	    
}
