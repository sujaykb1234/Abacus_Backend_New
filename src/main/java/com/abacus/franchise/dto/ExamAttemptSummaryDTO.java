package com.abacus.franchise.dto;

public class ExamAttemptSummaryDTO {

    private Integer attemptNo;
    private Long totalQuestions;
    private Long correctQuestions;
    private Long marks;

    public ExamAttemptSummaryDTO(Integer attemptNo,
            Long totalQuestions,
            Long correctQuestions,
            Long marks) {
        this.attemptNo = attemptNo;
        this.totalQuestions = totalQuestions;
        this.correctQuestions = correctQuestions;
        this.marks = marks;
    }

    public Integer getAttemptNo() {
        return attemptNo;
    }

    public Long getTotalQuestions() {
        return totalQuestions;
    }

    public Long getCorrectQuestions() {
        return correctQuestions;
    }

    public Long getMarks() {
        return marks;
    }
}