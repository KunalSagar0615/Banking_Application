package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.enumeration.AccountType;
import com.example.demo.model.AccountTypeConfig;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AccountTypeConfigRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {
	

	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private AccountTypeConfigRepository accountTypeConfigRepository;
	
	@Override
	public List<User> display() {
		return userRepository.findAll();
	}

	@Override
	public void delete(Integer empId) {
		userRepository.deleteById(empId);
	}

	
	@Transactional
	@Override
	public void updateMinBalanceByType(AccountType type, Double amount) {
		AccountTypeConfig config = accountTypeConfigRepository.findById(type).orElseThrow(()-> new RuntimeException("Configuration not found"));
		config.setMIN_BALANCE(amount);
	}

	
	@Transactional
	@Override
	public void updateWithdrawLimitByType(AccountType type, Double amount) {
		
		AccountTypeConfig config = accountTypeConfigRepository.findById(type).orElseThrow(()-> new RuntimeException("Configuration not found"));
		if(type==AccountType.CURRENT)
			throw new RuntimeException("You Cannot Set Withdraw Limit to CURRENT account");
		
		config.setWITHDRAW_LIMIT(amount);
	}


}
