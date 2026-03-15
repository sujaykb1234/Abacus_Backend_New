package com.abacus.franchise.dto;

import java.util.List;

import lombok.Value;

@Value
public class QuestionDTO {
	ExamDetailProjection examDetails;
	List<QuestionProjection> questions;
}
