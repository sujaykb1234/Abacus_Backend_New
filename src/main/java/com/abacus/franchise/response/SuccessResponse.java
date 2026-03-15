package com.abacus.franchise.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.abacus.franchise.utility.Messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse {

	private Object response;

	private Boolean status;

	private String message;

	private HttpStatusCode statusCode;
	
	
		
	public void kitsSentSuccessfully() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.kitSendSuccessfully;
	}
	
	public void addressNotFound() {
		this.status = false;
		this.message = Messages.addressNotFound;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.response = null;			
	}
	
	public void franchiesnotfound() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.NOT_EXTENDED;
		this.message = Messages.franchiesNotFound;
	}
	
	public void courseNotFound(String courseId) {
		this.response = null;
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = courseId+Messages.course_not_found;
	}
	
	public void courseNotFoundInFranchise() {
		this.response = null;
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.course_not_found_franchise;
	}
	
	public void courseFound(Object object) {
		this.status = true;
		this.message = Messages.course_found;
		this.statusCode = HttpStatus.CREATED;
		this.response = object;		
	}
	
	public void userNotFound() {
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.userNotFound;
        this.response = null;
	}

	
	public void userFoundResponse(Object object,String role) {
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = role+Messages.userFound;
        this.response = object;
	}
	
	public void loginSuccessfully(Object object) {
		this.response = object;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.admilogin;

	}
	
	public void wrongPassword() {
		this.status = false;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.message = Messages.wrongPassword;
	}
	
	public void userIsDeactivate() {
		this.status = false;
		this.message = Messages.accountIsDeactivate;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;		
	}
	
	public void usernameIncorrect() {
		this.status = false;
		this.message = Messages.correctUsername;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;		
	}
	
	public void basicDetailsIsNull() {
		this.status = false;
		this.message = Messages.basicDetailIsNull;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;		
	}
	
	public void addressDetailIsNull() {
		this.status = false;
		this.message = Messages.addressIsNull;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;		
	}

	public void rolesNotFound() {
		this.status = false;
		this.message = Messages.rolesNotFound;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.response = null;	
	}
	
	public void stateNotFound() {
		this.response = null;
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.state_not_found;
	}
	
	public void districtNotFound() {
		this.response = null;
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.district_not_found;
	}
	
	public void saveUserResponse(String accessToken) {
		this.status = true;
		this.message = Messages.save_user;
		this.statusCode = HttpStatus.CREATED;
		this.response = accessToken;		
	}
	
	
	public void loginCredentialIsNull() {
		this.status = false;
		this.message = Messages.usernamePasswordNull;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;			
	}

	public void mobileAlreadyExist() {
		this.status = false;
		this.message = Messages.mobileAlreadyExist;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;		
	}

	public void emailAlreadyExist() {
		this.status = false;
		this.message = Messages.emailAlreadyExist;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;			
	}

	public void productFound(Object object) {
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.productFound;
        this.response = object;		
	}
	
	public void productNotFound(String msg) {
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = msg+Messages.productNotFound;
        this.response = null;		
	}
		
	public void passwordRequired() {
		this.status = false;
		this.message = Messages.passwordRequired;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;
	}

	public void sendProductOrder() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.sendProductOrder;		
	}

	public void examNotFound() {
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.examNotFound;
        this.response = null;				
	}
	
	public void examFound(Object object) {
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.examFound;
        this.response = object;		
	}

	public void studentIdIsNull() {
		this.status = false;
		this.message = Messages.studentIsNull;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;			
	}

	public void studentNotFound() {
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.studentNotFound;
        this.response = null;						
	}

	public void examIdIsNull() {
		this.status = false;
		this.message = Messages.examIdIsNull;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;			
	}

	public void assignExamSuccessfully() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.assignExamSuccess;
		
	}

	public void studentFound(Object object) {
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.examFound;
        this.response = object;			
	}

	public void switchCourseSuccessfully() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.switchCourseSuccess;		
	}

	public void requestNotFound() {
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.requestNotFound;
        this.response = null;		
	}
	
	public void requestFound(Object object) {
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.requestFound;
        this.response = object;			
	}

	public void quantityIsLessThan() {
		this.status = false;
		this.message = Messages.quantityIsLess;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;			
	}

	public void examNotComplete() {
		this.status = false;
		this.message = Messages.examNotComplete;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;		
	}

	public void courseAlreadyExists() {
		this.status = false;
		this.message = Messages.courseAlreadyExists;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;		
	}

	public void reassignFailed() {
		this.status = false;
		this.message = Messages.reassignFailed;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.response = null;		
	}

	public void reassignSuccessfully() {
		this.response = null;
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.reassignSuccess;		
	}

	public void assignExamNotFound() {
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.notAssignExam;
        this.response = null;		
	}

	public void questionNotFound() {
		this.status = false;
		this.statusCode = HttpStatus.NOT_FOUND;
		this.message = Messages.questionNotFound;
        this.response = null;		
	}

	public void questionFound(Object object) {
		this.status = true;
		this.statusCode = HttpStatus.FOUND;
		this.message = Messages.questionFound;
        this.response = object;
	}

	public void examSendSuccessfully(Object object) {
		this.status = true;
		this.statusCode = HttpStatus.OK;
		this.message = Messages.sendExamSuccess;
        this.response = object;		
	}

	public void examNotSend() {
		this.status = true;
		this.statusCode = HttpStatus.BAD_REQUEST;
		this.message = Messages.failedToSendExam;
        this.response = null;		
	}
}
