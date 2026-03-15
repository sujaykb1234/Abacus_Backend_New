package com.abacus.franchise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abacus.franchise.response.SuccessResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	 
	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<SuccessResponse> handleResourceNotFound(ResourceNotFoundException ex) {

	        SuccessResponse response = new SuccessResponse();
	        response.setStatus(false);
	        response.setMessage(ex.getMessage());
	        response.setStatusCode(HttpStatus.NOT_FOUND);
	        response.setResponse(null);

	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(response);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<SuccessResponse> handleGenericException(Exception ex) {

	        SuccessResponse response = new SuccessResponse();
	        response.setStatus(false);
	        response.setMessage(ex.getMessage());
	        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setResponse(null);

	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

}
