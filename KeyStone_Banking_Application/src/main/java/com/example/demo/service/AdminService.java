package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.example.demo.model.User;

public interface AdminService {
	List<User> display();
	void delete(Integer empId);
}
