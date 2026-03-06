package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;


@RestController
@RequestMapping("/user")
public class userController {

	//to add new employee or login employee 
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public void add(@RequestBody User user) {
		userService.registerUser(user);
		
	}
	
	@GetMapping("/login/{email}/{pass}")
	public void loginUser(@PathVariable String email,@PathVariable String pass) {
		userService.loginUser(email, pass);
	}
	
	
}
