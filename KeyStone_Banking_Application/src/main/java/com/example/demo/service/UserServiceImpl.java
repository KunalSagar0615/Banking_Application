package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.email.EmailService;
import com.example.demo.enumeration.Role;
import com.example.demo.exception.AccountDetailsValidation;
import com.example.demo.exception.InvalidEmailFormate;
import com.example.demo.exception.InvalidPassword;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountDetailsValidation accountDetailsValidation;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public void registerUser(User user) {
		
		User user1= new User();
		
	    accountDetailsValidation.validateEmail(user.getEmail());
	    if(userRepository.findByEmail(user.getEmail()).isPresent()) {
	        throw new InvalidEmailFormate("Email already exists !");
	    }
	    accountDetailsValidation.validName(user.getName());
	    
	    System.out.println("email is "+user.getEmail());
	    
	    String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	    
	    
	    if(!user.getPassword().matches(pattern))
	    	throw new InvalidPassword("Invalid Password formate !");
	    
	    user1.setEmpId(user.getEmpId());
	    user1.setName(user.getName());
	    user1.setEmail(user.getEmail());
	    user1.setPassword(passwordEncoder.encode(user.getPassword()));
	    user1.setRole(Role.EMPLOYEE);
	    
	    String message = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Welcome</title></head><body style=\"font-family:Arial;background:#f4f6f8;padding:20px;\"><div style=\"max-width:600px;margin:auto;background:white;padding:30px;border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,0.1);\"><h2 style=\"color:#2E7D32;\">Welcome to Keystone Bank 🎉</h2><p>Dear " + user.getName() + ",</p><p>We are delighted to welcome you to <strong>Keystone Bank</strong>. Your journey with us begins today, and we are excited to have you as part of our growing team.</p><p>Your contribution will play an important role in delivering excellent banking services to our customers.</p><p>If you have any questions or need assistance during your onboarding process, please feel free to contact the HR or administration team.</p><br><p>We wish you great success in your role.</p><p>Best Regards,<br><strong>Keystone Banking Team</strong></p></div></body></html>";
	    emailService.sendMail(user.getEmail(), "Welcome to Keystone Bank", message);
	    userRepository.save(user1);
	}


	
	@Override
	public String loginUser(String email, String pass) {

	    Optional<User> user = userRepository.findByEmail(email);

	    if(user.isPresent() && passwordEncoder.matches(pass, user.get().getPassword())) {

	        return JwtUtil.generateToken(user.get().getEmail(),user.get().getRole().name());
	    }

	    throw new InvalidPassword("Invalid email or password");
	}



	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//fetch user present in the db
		User user = userRepository.findByEmail(email).get();
				
		System.out.println("email is "+user.getEmail());
		
		//inject db user in spring security User object
		return org.springframework.security.core.userdetails.User.
				builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();
	}



	


}
