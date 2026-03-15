package com.abacus.franchise.dto;

import java.util.UUID;

public interface ExamDetailProjection {
	  UUID getAssignExamId();
	  Integer getExamTime();
	  Integer getQuestionCount();
	  String getExamName();
}
