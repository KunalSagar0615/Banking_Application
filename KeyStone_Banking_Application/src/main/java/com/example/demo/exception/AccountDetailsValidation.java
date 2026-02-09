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
	
	public void validName(String name) {
		String pattern = "^[A-Za-z ]+$";
		if(!name.matches(pattern))
			throw new InvalidNameException("Name only contain alphabets !");
		
		if(name.length()<2)
			throw new InvalidNameException("Invalid Name");
	}
	
	public void validAdhar(Long adhaarNum) {
		String aadhaarPattern = "^[2-9][0-9]{11}$";
		String adhaar=String.valueOf(adhaarNum);
		
		if(!adhaar.matches(aadhaarPattern))
			throw new InvalidAdharNumber("Enter valid adhar number!!");
	}
	
	public void validateAmount(Double amt) {
		if(amt==null)
			throw new InvalidAmountException("Enter amount first !!");
		
		if(amt<0)
			throw new InvalidAmountException("Amount cannot be negative !!");		
	}
	
}
