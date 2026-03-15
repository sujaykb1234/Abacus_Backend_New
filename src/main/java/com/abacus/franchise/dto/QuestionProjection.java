package com.abacus.franchise.dto;

import java.util.UUID;

public interface QuestionProjection {
        UUID getQuestionId();
	    String getQuestionType();
	    String getQuestionText();
	    String getQuestionLink();
	    String getOptionA();
	    String getOptionB();
	    String getOptionC();
	    String getOptionD();
}
