package com.abacus.franchise.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.abacus.franchise.enums.ExamMode;
import com.abacus.franchise.enums.ExamStatus;

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
@Table(name = "assign_exam")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignExam {
	
	@Id
	@UuidGenerator
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "assign_exam_id", length = 36, updatable = false, nullable = false)
	private UUID assignExamId;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "exam_id", length = 36, updatable = false, nullable = false)
	private UUID examId;
	
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "franchise_id", length = 36, updatable = false, nullable = false)
	private UUID franchiseId;
	
    @Column(name = "exam_time", nullable = false, length = 255)
	private int examTime;
    
    @Column(name = "correct_que", nullable = false, length = 255)
  	private int correctQue;
    
    @Column(name = "exam_attempt", nullable = false, length = 255)
   	private int examAttempt = 1;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "exam_mode", nullable = false, length = 255)
	private ExamMode examMode;
    
    @Column(name = "upload_final_paper_name", length = 100)
    private String uploadFinalPaperName;
    
    @Column(name = "upload_final_paper_link", length = 100)
    private String uploadFinalPaperLink;
    
    @Column(name = "exam_marks", nullable = false, length = 255)
	private int examMarks;
	
	@Column(name = "assign_date")
	private LocalDateTime assignDate;
	
	@Column(name = "submit_date")
	private LocalDateTime submitDate;

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Enumerated(EnumType.STRING)
    @Column(name = "exam_status", nullable = false, length = 255)
	private ExamStatus examStatus = ExamStatus.ASSIGNED;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "created_by", length = 36)
	private UUID createdBy;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "updated_by", length = 36)
	private UUID updatedBy;

	@Builder.Default
	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;

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
