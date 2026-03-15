package com.abacus.franchise.viewModels;

import java.util.UUID;

import lombok.Value;

@Value
public class QuestionsAnswerRequest {
    private UUID questionId;
    private String chosenAnswer;
}
