package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.email.EmailService;
import com.example.demo.exception.AccountDetailsValidation;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountDetailsValidation accountDetailsValidation;
	
	@Autowired
	private EmailService emailService;
	
	
	@Override
	public void registerUser(User user) {

	    accountDetailsValidation.validateEmail(user.getEmail());
	    User temp=userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("Email already exists 1!"));	 
	    
	    accountDetailsValidation.validName(user.getName());
	    
	    String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8}$";
	    
	    if(!user.getPassword().matches(pattern))
	    	throw new RuntimeException("Invalid Password formate !");
	    
	    userRepository.save(user);
	}


	
	@Override
	public boolean loginUser(String email,String pass) {
		
		User temp=userRepository.findByEmail(email).orElse(null);
		if(temp==null)
			return false;
		return pass.equals(temp.getPassword());		
			
	}



	


}
