package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
	void registerUser(User user);
	String loginUser(String email,String pass);
	
}
