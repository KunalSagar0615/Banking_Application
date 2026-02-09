package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BalanceDTO;
import com.example.demo.dto.UpdateAccountDTO;
import com.example.demo.model.Account;

public interface AccountService {

	//create
	void createAccount(Account account);
	
	//display
	List<Account> getAllAccounts();
	
	//delete
	Account closeAccount(Long acno);
	
	//searching
	Account getByAccountNumber(Long acno);
	Account getByEmail(String email);
	Account getByMobile(String mobile);
	
	//check balance
	BalanceDTO getBalance(Long acno);
	
	//update Account 
	Account update(Long acno,UpdateAccountDTO updateAccountDto);
	
	//withdraw amount
	BalanceDTO withdrawAmount(Long acno,Double amount);
	
	//deposit amount
	BalanceDTO depositAmount(Long acno,Double amount);
	
}
