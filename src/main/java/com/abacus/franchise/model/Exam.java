package com.abacus.franchise.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.abacus.franchise.enums.ExamMode;

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
@Table(name = "exam")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {

	@Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "exam_id", length = 36, updatable = false, nullable = false)
    private UUID examId;

    @Column(name = "exam_time", nullable = false, length = 255)
	private int examTime;
    
    @Column(name = "exam_name", nullable = false, length = 255)
	private String examName;
    
    @Column(name = "image_que_percentage", nullable = false, length = 255)
	private int imageQuePercentage;
    
    @Column(name = "question_count", nullable = false, length = 255)
	private int questionCount;
    
    
    @JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "course_id", length = 36, updatable = false, nullable = false)
	private UUID courseId;
    
    @Column(name = "final_paper_name", length = 100)
    private String finalPaperName;
    
    @Column(name = "final_paper_link", length = 100)
    private String finalPaperLink;
    
    @Column(name = "practice_paper_name", length = 100)
    private String practicePaperName;
    
    @Column(name = "practice_paper_link", length = 100)
    private String practicePaperLink;

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
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
	
}
