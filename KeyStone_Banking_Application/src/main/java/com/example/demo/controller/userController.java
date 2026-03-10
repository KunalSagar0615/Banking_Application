package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
public class userController {

	//to add new employee or login employee 
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public void add(@RequestBody User user) {
		userService.registerUser(user);
		
	}
	
	@GetMapping("/login/{email}/{pass}")
	public Map<String,String> loginUser(@PathVariable String email,@PathVariable String pass){

	    String token = userService.loginUser(email, pass);

	    Map<String,String> response = new HashMap<>();
	    response.put("token", token);

	    return response;
	}
	
	
}
