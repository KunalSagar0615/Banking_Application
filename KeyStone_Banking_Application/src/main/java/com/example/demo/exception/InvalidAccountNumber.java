package com.example.demo.exception;

public class InvalidAccountNumber extends RuntimeException{

	public InvalidAccountNumber(String message) {
		super(message);
	}
	
}
