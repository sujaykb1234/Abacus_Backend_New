package com.abacus.franchise.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abacus.franchise.response.SuccessResponse;
import com.abacus.franchise.service.UsersService;
import com.abacus.franchise.viewModels.AuthRequest;
import com.abacus.franchise.viewModels.SubmitExamRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("abacus/v1/student/")
@CrossOrigin("*")
public class StudentController {
	
	@Autowired
	UsersService usersService;
	
	@PostMapping("login")
	public ResponseEntity<SuccessResponse> loginUsers(@RequestBody AuthRequest authRequest,HttpServletRequest request) {
		
		UUID userId = (UUID) request.getAttribute("userId");
		String role = (String) request.getAttribute("role");
     
		authRequest.setRolename(role);
		authRequest.setUserId(userId);
		
	   SuccessResponse response =usersService.loginUsers(authRequest);
	   return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	@GetMapping("getStudentById")
	public ResponseEntity<SuccessResponse> getUsersById(HttpServletRequest request) {

	    UUID userId = (UUID) request.getAttribute("userId");
	    String role = (String) request.getAttribute("role");

	    SuccessResponse response =usersService.getUsersById(userId, role);
	    return ResponseEntity
	            .status(response.getStatusCode())
	            .body(response);
	}
	
	@GetMapping("getAllCourseExamByStudent")
	public ResponseEntity<SuccessResponse> getAllCourseExamByStudent(HttpServletRequest request) {

	    UUID userId = (UUID) request.getAttribute("userId");

	    SuccessResponse response =usersService.getAllCourseExamByStudent(userId.toString());
	    return ResponseEntity
	            .status(response.getStatusCode())
	            .body(response);
	}
	
	@GetMapping("getAllQuestionByStudent")
	public ResponseEntity<SuccessResponse> getAllQuestionByStudent(HttpServletRequest request) {

	    UUID userId = (UUID) request.getAttribute("userId");

	    SuccessResponse response =usersService.getAllQuestionByStudent(userId.toString());
	    return ResponseEntity
	            .status(response.getStatusCode())
	            .body(response);
	}
	


	
	@PostMapping("submitExam")
	public ResponseEntity<SuccessResponse> submitExam(@RequestBody SubmitExamRequest examRequest,
			HttpServletRequest request) {

	    UUID userId = (UUID) request.getAttribute("userId");
	    examRequest.setStudentId(userId);
	    
	    SuccessResponse response =usersService.submitExam(examRequest);
	    return ResponseEntity
	            .status(response.getStatusCode())
	            .body(response);
	}

	
	

}
