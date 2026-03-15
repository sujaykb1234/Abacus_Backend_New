package com.abacus.franchise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abacus.franchise.repo.UsersRepository;
import com.abacus.franchise.response.SuccessResponse;
import com.abacus.franchise.service.UsersService;
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
		return usersService.saveOrUpdateUsers(user,profileImage,documentImage,request);
	}
}
