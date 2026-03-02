package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.EmployeeService;




@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/display-employees")
	public ResponseEntity<User> display() {
		User emp=(User) adminService.display();
		return ResponseEntity.ok(emp);
	}
	
	@PutMapping("/update/saving/min-balance/{amount}")
	public String putMethodName(@PathVariable Integer id, @RequestBody String entity) {
		
		return entity;
	}
	
	
	
}


