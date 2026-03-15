package com.abacus.franchise.dto;

public class CourseKitDTO {

    private String courseName;
    private Integer kitCount;
    
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getKitCount() {
		return kitCount;
	}
	public void setKitCount(Integer kitCount) {
		this.kitCount = kitCount;
	}
	
	public CourseKitDTO(String courseName, Integer kitCount) {
		super();
		this.courseName = courseName;
		this.kitCount = kitCount;
	}
    
    
}
