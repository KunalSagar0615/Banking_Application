package com.example.demo.exception;

public class AccountDetailsValidation extends RuntimeException {

	public void validateAccountNumber(Long acno) {
		
		if(acno==null)
			throw new InvalidAccountNumber("Account number cannot be empty !!");
		
		if(acno<=0)
			throw new InvalidAccountNumber("Account number cannot be negative!!");
		
	}
	
	public void validateEmail(String email) {
		String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

		
		if(email.trim().length()==0)
			throw new InvalidEmailFormate("Email required !!");
		
		if(!email.matches(pattern))
			throw new InvalidEmailFormate("Invalid Email formate !!");
		
	}
	
	public void validateMobileNumber(String mob) {
		String pattern = "^[6-9]\\d{9}$";

		if(mob.trim().length()>10 || mob.trim().length() < 10)
			throw new InvalidMobileNumber("Enter valid mobile number!!");
		
		if(!mob.matches(pattern))
			throw new InvalidMobileNumber("Mobile number must be digits!!");
	}
	
}
