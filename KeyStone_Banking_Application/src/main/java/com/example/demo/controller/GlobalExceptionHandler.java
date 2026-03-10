package com.example.demo.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.InvalidAccountNumber;
import com.example.demo.exception.InvalidAdharNumber;
import com.example.demo.exception.InvalidAmountException;
import com.example.demo.exception.InvalidEmailFormate;
import com.example.demo.exception.InvalidMobileNumber;
import com.example.demo.exception.InvalidNameException;

@RestControllerAdvice

public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidAccountNumber.class)
	public ResponseEntity<?> InvalidAccountNumber(InvalidAccountNumber e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidAdharNumber.class)
	public ResponseEntity<?> InvalidAdharNumber(InvalidAdharNumber e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidAmountException.class)
	public ResponseEntity<?> InvalidAmountException(InvalidAmountException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidEmailFormate.class)
	public ResponseEntity<?> InvalidEmailFormate(InvalidEmailFormate e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidMobileNumber.class)
	public ResponseEntity<?> InvalidMobileNumber(InvalidMobileNumber e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidNameException.class)
	public ResponseEntity<?> InvalidNameException(InvalidNameException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<?> handleAccountNotFound(AccountNotFoundException e) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body(e.getMessage());
	}
}
