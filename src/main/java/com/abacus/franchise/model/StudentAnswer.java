package com.abacus.franchise.model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAnswer {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "student_answer_id", length = 36, nullable = false, updatable = false)
    private UUID studentAnswerId;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "student_id", length = 36)
    private UUID studentId;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "question_id", length = 36)
    private UUID questionId;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "assign_exam_id", length = 36)
    private UUID assignExamId;

    @Column(name = "exam_attempt")
    private int examAttempt;

    @Column(name = "chosen_answer", length = 1)
    private String chosenAnswer;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

}
