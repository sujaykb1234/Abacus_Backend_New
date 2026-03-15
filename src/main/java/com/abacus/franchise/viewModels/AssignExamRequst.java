package com.abacus.franchise.viewModels;

import java.util.List;
import java.util.UUID;

import com.abacus.franchise.enums.ExamMode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignExamRequst {
	
	private UUID franchiseId;
	
	private UUID examId;

	private int examTime;

	private ExamMode examMode;

	private List<UUID> studentId;

}
