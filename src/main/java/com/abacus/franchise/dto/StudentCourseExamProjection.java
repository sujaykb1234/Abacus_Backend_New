package com.abacus.franchise.dto;

public interface StudentCourseExamProjection {

    String getCourseName();
    Integer getDurationDays();
    String getCourseType();
    Boolean getCourseActive();

    String getExamName();
    Integer getQuestionCount();
    Integer getImageQuePercentage();

    java.time.LocalDateTime getAssignDate();
    String getExamMode();
    Integer getExamTime();
    Integer getExamMarks();
    String getExamStatus();
    java.time.LocalDateTime getSubmitDate();
    java.time.LocalDateTime getStartDate();
    Integer getExamAttempt();
}
