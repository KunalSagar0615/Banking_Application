package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
	void registerUser(User user);
	boolean loginUser(String email,String pass);
	
}
