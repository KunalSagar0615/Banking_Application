package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enumeration.AccountType;
import com.example.demo.model.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.EmployeeService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	
	@GetMapping("/display-employees")
	public ResponseEntity<List<User>> display() {
	    List<User> emp = adminService.display();
	    return ResponseEntity.ok(emp);
	}
	
	@DeleteMapping("/delete-employee/{empId}")
	public void delete(@PathVariable Integer empId) {
		adminService.delete(empId);
	}
		
	@PutMapping("/update-min-balance/{type}/{amount}")
	public void updateMinBalanceByType(@PathVariable AccountType type, @PathVariable Double amount) {
		adminService.updateMinBalanceByType(type, amount);
	}
	
	@PutMapping("/update-withdraw-limit/{type}/{amount}")
	public void updateWithdrawLimitByType(@PathVariable AccountType type, @PathVariable Double amount) {
		adminService.updateWithdrawLimitByType(type, amount);
	}
	
}


