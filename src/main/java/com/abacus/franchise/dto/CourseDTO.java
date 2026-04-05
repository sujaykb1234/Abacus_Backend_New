package com.abacus.franchise.dto;

import java.util.UUID;

import com.abacus.franchise.enums.CourseType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
	private String courseId;
	private String courseName;
	private CourseType courseType;
	private int durationDays;
	private int noOfBooks;
	private UUID userId;

}
