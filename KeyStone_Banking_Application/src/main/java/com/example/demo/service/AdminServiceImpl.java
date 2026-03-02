package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {
	

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> display() {
		return userRepository.findAll();
	}

	@Override
	public void delete(Integer empId) {
		userRepository.deleteById(empId);
	}		
	

}
