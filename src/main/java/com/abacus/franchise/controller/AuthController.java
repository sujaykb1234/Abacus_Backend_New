package com.abacus.franchise.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abacus.franchise.repo.UsersRepository;
import com.abacus.franchise.response.SuccessResponse;
import com.abacus.franchise.service.UsersService;
import com.abacus.franchise.viewModels.AuthRequest;
import com.abacus.franchise.viewModels.RefreshTokenRequest;
import com.abacus.franchise.viewModels.UserViewModel;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("abacus/v1/auth/")
public class AuthController {

	@Autowired
	UsersService usersService;

	@Autowired
	UsersRepository usersRepository;

	@PostMapping("saveOrUpdateUser")
	public SuccessResponse saveOrUpdateUsers(@ModelAttribute UserViewModel user,
			@RequestParam(required = false) MultipartFile profileImage,
			@RequestParam(required = false) MultipartFile documentImage,
			HttpServletRequest request) {
		return usersService.saveOrUpdateUsers(user, profileImage, documentImage, request);
	}

	@PostMapping("login")
	public ResponseEntity<SuccessResponse> loginUsers(@RequestBody AuthRequest authRequest) {

		SuccessResponse response = usersService.loginUsers(authRequest);

		return ResponseEntity
				.status(response.getStatusCode())
				.body(response);
	}

	@PostMapping("logout")
	public ResponseEntity<SuccessResponse> logoutUsers() {
		// Get user ID from security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UUID userId = null;

		if (authentication != null && authentication.getPrincipal() instanceof String) {
			// Extract userId from JWT token (assuming it's stored in request attributes by
			// JwtFilter)
			userId = (UUID) org.springframework.web.context.request.RequestContextHolder
					.currentRequestAttributes()
					.getAttribute("userId", org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST);
		}

		if (userId == null) {
			SuccessResponse errorResponse = new SuccessResponse();
			errorResponse.setStatus(false);
			errorResponse.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
			errorResponse.setMessage("User not authenticated");
			return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body(errorResponse);
		}

		SuccessResponse response = usersService.logoutUsers(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@PostMapping("refresh")
	public ResponseEntity<SuccessResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		SuccessResponse response = usersService.refreshToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
