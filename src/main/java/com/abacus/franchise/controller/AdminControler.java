package com.abacus.franchise.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abacus.franchise.dto.CourseDTO;
import com.abacus.franchise.response.SuccessResponse;
import com.abacus.franchise.service.UsersService;
import com.abacus.franchise.viewModels.FranchiseRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("abacus/v1/admin/")
@CrossOrigin("*")
public class AdminControler {
	@Autowired
	UsersService usersService;

	@PostMapping("getAllFranchise")
	public ResponseEntity<SuccessResponse> getAllFranchise(@RequestBody FranchiseRequest franchiseRequest,
			HttpServletRequest request) {

		SuccessResponse response = usersService.getAllFranchises(franchiseRequest);
		return ResponseEntity
				.status(response.getStatusCode())
				.body(response);
	}

	@GetMapping("getAllKitRequestsByFranchiseId")
	public ResponseEntity<SuccessResponse> getAllKitRequests(
			@RequestParam String franchiseId,
			HttpServletRequest request) {
		SuccessResponse response = usersService.getAllKitRequest(franchiseId);
		return ResponseEntity
				.status(response.getStatusCode())
				.body(response);
	}

	@PostMapping("assignCourseToFranchise")
	public ResponseEntity<SuccessResponse> assignCourseToFranchise(
			@RequestParam String franchiseId,
			@RequestParam String courseId,
			HttpServletRequest request) {
		UUID userId = (UUID) request.getAttribute("userId");
		SuccessResponse response = usersService.assignCourseToFranchise(franchiseId, courseId, userId);
		return ResponseEntity
				.status(response.getStatusCode())
				.body(response);
	}

	@PostMapping("updateFranchiseRole")
	public ResponseEntity<SuccessResponse> updateUserRole(
			@RequestParam String franchiseId,
			@RequestParam String roleId,
			HttpServletRequest request) {
		UUID userId = (UUID) request.getAttribute("userId");
		SuccessResponse response = usersService.updateUserRole(franchiseId, roleId, userId);
		return ResponseEntity
				.status(response.getStatusCode())
				.body(response);
	}

	@GetMapping("getAllActiveCourses")
	public ResponseEntity<SuccessResponse> getAllActiveCourses(HttpServletRequest request) {
		SuccessResponse response = usersService.getAllActiveCourses();
		return ResponseEntity
				.status(response.getStatusCode())
				.body(response);
	}

	@PostMapping("saveOrUpdateCourse")
	public ResponseEntity<SuccessResponse> saveOrUpdateCourse(@RequestBody CourseDTO courseDTO,
			HttpServletRequest request) {
		UUID userId = (UUID) request.getAttribute("userId");
		courseDTO.setUserId(userId);
		SuccessResponse response = usersService.saveOrUpdateCourse(courseDTO);
		return ResponseEntity
				.status(response.getStatusCode())
				.body(response);
	}

	@DeleteMapping("deleteCourse")
	public ResponseEntity<SuccessResponse> deleteCourse(@RequestParam String courseId,
			HttpServletRequest request) {
		SuccessResponse response = usersService.deleteCourse(courseId);
		return ResponseEntity
				.status(response.getStatusCode())
				.body(response);
	}

}
