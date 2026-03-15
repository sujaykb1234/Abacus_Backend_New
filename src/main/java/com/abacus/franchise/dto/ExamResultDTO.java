package com.abacus.franchise.dto;

import lombok.Value;

@Value
public class ExamResultDTO {
   int totalQuestion;
   int correctQuestion;
   int marks;
}
