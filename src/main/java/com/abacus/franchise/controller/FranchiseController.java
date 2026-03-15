package com.abacus.franchise.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abacus.franchise.response.SuccessResponse;
import com.abacus.franchise.service.UsersService;
import com.abacus.franchise.viewModels.AssignExamRequst;
import com.abacus.franchise.viewModels.AuthRequest;
import com.abacus.franchise.viewModels.KitRequest;
import com.abacus.franchise.viewModels.SwitchCourseRequest;
import com.abacus.franchise.viewModels.ViewProductRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("abacus/v1/franchise/")
@CrossOrigin("*")
public class FranchiseController {
	
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
	
	@GetMapping("getfranchiseById")
	public ResponseEntity<SuccessResponse> getUsersById(HttpServletRequest request) {

	    UUID userId = (UUID) request.getAttribute("userId");
	    String role = (String) request.getAttribute("role");

	    SuccessResponse response =usersService.getUsersById(userId, role);
	    return ResponseEntity
	            .status(response.getStatusCode())
	            .body(response);
	}
	
	@GetMapping("/getStudentByFranchiseId")
	public ResponseEntity<SuccessResponse> getStudentByFranchiseId(HttpServletRequest request) {
	    UUID franchiseId = (UUID) request.getAttribute("userId");
		
		SuccessResponse response =usersService.getStudentByFranchiseId(franchiseId);
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	@GetMapping("/getAllCoursesByFranchiseId")
	public ResponseEntity<SuccessResponse> getAllCoursesByFranchiseId(HttpServletRequest request) {
	    UUID franchiseId = (UUID) request.getAttribute("userId");

		SuccessResponse response =usersService.getAllCoursesByFranchiseId(franchiseId);
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	@PostMapping("/sendCourseKitRequest")
	public ResponseEntity<SuccessResponse> sendCourseKitRequest(@RequestBody KitRequest kitRequest,
			HttpServletRequest request) {
	    UUID franchiseId = (UUID) request.getAttribute("userId");
	    kitRequest.setFranchiseId(franchiseId);
		
		SuccessResponse response =usersService.sendCourseKitRequest(kitRequest);
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	
	@GetMapping("/getAllProductDetail")
	public ResponseEntity<SuccessResponse> getAllProductDetail() {
		SuccessResponse response =usersService.getAllProductDetail();
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	@PostMapping("sendProductRequest")
	public ResponseEntity<SuccessResponse> sendProductRequest(@RequestBody List<ViewProductRequest> productRequests,
			HttpServletRequest request) {
	    UUID franchiseId = (UUID) request.getAttribute("userId");

		SuccessResponse response =usersService.sendProductRequest(franchiseId, productRequests);
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	@GetMapping("getAllExamDetailByCourseId/{courseId}")
	public ResponseEntity<SuccessResponse> getAllExamDetailByCourseId(@PathVariable String courseId) {
	   SuccessResponse response =usersService.getAllExamDetailByCourseId(courseId);
	   return ResponseEntity
	            .status(response.getStatusCode())
	            .body(response);
	}
	
	@PostMapping("studentAssignExam")
	public ResponseEntity<SuccessResponse> studentAssignExam(@RequestBody AssignExamRequst assignExamRequst,
			HttpServletRequest request) {
	    UUID franchiseId = (UUID) request.getAttribute("userId");
	    assignExamRequst.setFranchiseId(franchiseId);
		SuccessResponse response =usersService.studentAssignExam(assignExamRequst);
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
		
	@GetMapping("getFinalPaperByCourseId/{courseId}")
	public ResponseEntity<SuccessResponse> getFinalPaperByCourseId(@PathVariable String courseId) {
	   SuccessResponse response =usersService.getFinalPaperByCourseId(courseId);
	   return ResponseEntity
	            .status(response.getStatusCode())
	            .body(response);
	}
	
	
	@GetMapping("getPracticePaperByCourseId/{courseId}")
	public ResponseEntity<SuccessResponse> getPracticePaperByCourseId(@PathVariable String courseId) {
	   SuccessResponse response =usersService.getPracticePaperByCourseId(courseId);
	   return ResponseEntity
	            .status(response.getStatusCode())
	            .body(response);
	}

	@GetMapping("getAllOfflineExamStudentsByCourseId/{courseId}")
	public ResponseEntity<SuccessResponse> getAllOfflineExamStudentsByCourseId(@PathVariable String courseId,
			HttpServletRequest request) {
		UUID franchiseId = (UUID) request.getAttribute("userId");

		SuccessResponse response =usersService.getAllOfflineExamStudentsByCourseId(courseId,franchiseId.toString());
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	@GetMapping("getAllOnlineExamResultStudentsByCourseId/{courseId}")
	public ResponseEntity<SuccessResponse> getAllOnlineExamResultStudentsByCourseId(@PathVariable String courseId,
			HttpServletRequest request) {
		UUID franchiseId = (UUID) request.getAttribute("userId");

		SuccessResponse response =usersService.getAllOnlineExamResultStudentsByCourseId(courseId,franchiseId.toString());
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	
	@GetMapping("getAllCompleteCoursesStudentByFranchiseId")
	public ResponseEntity<SuccessResponse> getAllCompleteCoursesStudentByFranchiseId(HttpServletRequest request) {
		UUID franchiseId = (UUID) request.getAttribute("userId");

		SuccessResponse response =usersService.getAllCompleteCoursesStudentByFranchiseId(franchiseId.toString());
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}

	
	@PostMapping("switchCoursesByStudentOrFranchiseId")
	public ResponseEntity<SuccessResponse> changeCoursesByStudentOrFranchiseId(@RequestBody SwitchCourseRequest courseRequest,
			HttpServletRequest request) {
		UUID franchiseId = (UUID) request.getAttribute("userId");
		courseRequest.setFranchiseId(franchiseId.toString());
		SuccessResponse response =usersService.changeCoursesByStudentOrFranchiseId(courseRequest);
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	
	@GetMapping("getAllProductRequest")
	public ResponseEntity<SuccessResponse> getAllProductRequest(HttpServletRequest request) {
		UUID franchiseId = (UUID) request.getAttribute("userId");

		SuccessResponse response =usersService.getAllProductRequest(franchiseId.toString());
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	
	@GetMapping("getAllKitRequest")
	public ResponseEntity<SuccessResponse> getAllKitRequest(HttpServletRequest request) {
		UUID franchiseId = (UUID) request.getAttribute("userId");

		SuccessResponse response =usersService.getAllKitRequest(franchiseId.toString());
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	
	
	@GetMapping("getAllUnAssignStudentByExamId/{examId}")
	public ResponseEntity<SuccessResponse> getAllUnAssignStudentByExamId(@PathVariable String examId ) {
		SuccessResponse response =usersService.getAllUnAssignStudentByExamId(examId);
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}
	

	@PostMapping("requestToReAssignExam")
	public ResponseEntity<SuccessResponse> requestToReAssignExam(@RequestBody AssignExamRequst assignExamRequst,
			HttpServletRequest request) {
	   
		SuccessResponse response =usersService.requestToReAssignExam(assignExamRequst);
		 return ResponseEntity
		            .status(response.getStatusCode())
		            .body(response);
	}


}
