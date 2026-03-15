package com.abacus.franchise.viewModels;

import java.util.List;
import java.util.UUID;

import com.abacus.franchise.enums.ExamType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubmitExamRequest {

	UUID assignExamId;
	UUID studentId;
	List<QuestionsAnswerRequest> questionsAnswerRequest;
	String startTime;
	String endTime;
	ExamType examType;
}
