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

@RestController
@RequestMapping("abacus/v1/users/")
@CrossOrigin("*")
public class UsersController {
	
	@Autowired
	UsersService usersService;
	
	
	
	
//franchise	
	
//	@PostMapping("login")
//	public ResponseEntity<SuccessResponse> loginUsers(@RequestBody AuthRequest authRequest) {
//		
//		 SuccessResponse response =usersService.loginUsers(authRequest);
//
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//	
//	@GetMapping("/getUsersById/{userId}/{roleName}")
//	public ResponseEntity<SuccessResponse> getUsersById(
//	        @PathVariable UUID userId,
//	        @PathVariable String roleName) {
//
//	    SuccessResponse response =usersService.getUsersById(userId, roleName);
//	    return ResponseEntity
//	            .status(response.getStatusCode())
//	            .body(response);
//	}
//	
//	@GetMapping("/getStudentByFranchiseId/{franchiseId}")
//	public ResponseEntity<SuccessResponse> getStudentByFranchiseId(@PathVariable UUID franchiseId) {
//		SuccessResponse response =usersService.getStudentByFranchiseId(franchiseId);
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//
//	@GetMapping("/getAllCoursesByFranchiseId/{franchiseId}")
//	public ResponseEntity<SuccessResponse> getAllCoursesByFranchiseId( @PathVariable UUID franchiseId) {
//		SuccessResponse response =usersService.getAllCoursesByFranchiseId(franchiseId);
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//
//	@PostMapping("/sendCourseKitRequest")
//	public ResponseEntity<SuccessResponse> sendCourseKitRequest(@RequestBody KitRequest kitRequest) {
//		SuccessResponse response =usersService.sendCourseKitRequest(kitRequest);
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//	
//	@GetMapping("/getAllProductDetail")
//	public ResponseEntity<SuccessResponse> getAllProductDetail() {
//		SuccessResponse response =usersService.getAllProductDetail();
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//	
//	@PostMapping("sendProductRequest/{franchiseId}")
//	public ResponseEntity<SuccessResponse> sendProductRequest(@PathVariable UUID franchiseId,
//		@RequestBody List<ViewProductRequest> productRequests) {
//		SuccessResponse response =usersService.sendProductRequest(franchiseId, productRequests);
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//	
//	@GetMapping("getAllExamDetailByCourseId/{courseId}")
//	public ResponseEntity<SuccessResponse> getAllExamDetailByCourseId(@PathVariable String courseId) {
//	   SuccessResponse response =usersService.getAllExamDetailByCourseId(courseId);
//	   return ResponseEntity
//	            .status(response.getStatusCode())
//	            .body(response);
//	}
//
//	@PostMapping("studentAssignExam")
//	public ResponseEntity<SuccessResponse> studentAssignExam(@RequestBody AssignExamRequst assignExamRequst) {
//		SuccessResponse response =usersService.studentAssignExam(assignExamRequst);
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//
//	@GetMapping("getFinalPaperByCourseId/{courseId}")
//	public ResponseEntity<SuccessResponse> getFinalPaperByCourseId(@PathVariable String courseId) {
//	   SuccessResponse response =usersService.getFinalPaperByCourseId(courseId);
//	   return ResponseEntity
//	            .status(response.getStatusCode())
//	            .body(response);
//	}
//	
//	@GetMapping("getPracticePaperByCourseId/{courseId}")
//	public ResponseEntity<SuccessResponse> getPracticePaperByCourseId(@PathVariable String courseId) {
//	   SuccessResponse response =usersService.getPracticePaperByCourseId(courseId);
//	   return ResponseEntity
//	            .status(response.getStatusCode())
//	            .body(response);
//	}
//
////	@GetMapping("getAllOfflineExamStudentsByCourseId/{courseId}")
////	public ResponseEntity<SuccessResponse> getAllOfflineExamStudentsByCourseId(@PathVariable String courseId) {
////		SuccessResponse response =usersService.getAllOfflineExamStudentsByCourseId(courseId);
////		 return ResponseEntity
////		            .status(response.getStatusCode())
////		            .body(response);
////	}
////	
////	@GetMapping("getAllOnlineExamResultStudentsByCourseId/{courseId}")
////	public ResponseEntity<SuccessResponse> getAllOnlineExamResultStudentsByCourseId(@PathVariable String courseId) {
////		SuccessResponse response =usersService.getAllOnlineExamResultStudentsByCourseId(courseId);
////		 return ResponseEntity
////		            .status(response.getStatusCode())
////		            .body(response);
////	}
//	
//	
//	@GetMapping("getAllCompleteCoursesStudentByFranchiseId/{franchiseId}")
//	public ResponseEntity<SuccessResponse> getAllCompleteCoursesStudentByFranchiseId(@PathVariable String franchiseId) {
//		SuccessResponse response =usersService.getAllCompleteCoursesStudentByFranchiseId(franchiseId);
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//
//	
//	@PostMapping("switchCoursesByStudentOrFranchiseId")
//	public ResponseEntity<SuccessResponse> changeCoursesByStudentOrFranchiseId(@RequestBody SwitchCourseRequest courseRequest) {
//		SuccessResponse response =usersService.changeCoursesByStudentOrFranchiseId(courseRequest);
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//	
//	
//	@GetMapping("getAllProductRequest/{franchiseId}")
//	public ResponseEntity<SuccessResponse> getAllProductRequest(@PathVariable String franchiseId) {
//		SuccessResponse response =usersService.getAllProductRequest(franchiseId);
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//	
//	
//	@GetMapping("getAllKitRequest/{franchiseId}")
//	public ResponseEntity<SuccessResponse> getAllKitRequest(@PathVariable String franchiseId) {
//		SuccessResponse response =usersService.getAllKitRequest(franchiseId);
//		 return ResponseEntity
//		            .status(response.getStatusCode())
//		            .body(response);
//	}
//	


	

}
