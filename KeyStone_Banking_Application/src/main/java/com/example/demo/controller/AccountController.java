package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AccountResponseDTO;
import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.SavingAccount;
import com.example.demo.service.AccountService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@PostMapping("/create-saving-account")
	public void createSavingAccount(@RequestBody SavingAccount savingAccount) {
	    accountService.createAccount(savingAccount);
	}

	@PostMapping("/create-current-account")
	public void createCurrentAccount(@RequestBody CurrentAccount currentAccount) {
	    accountService.createAccount(currentAccount);
	}
	
	@GetMapping("/display-all-accounts")
	public List<AccountResponseDTO> getAllAccounts(){
		return accountService.getAllAccounts();
	}
	
	@GetMapping("/display-all-saving-accounts")
	public List<AccountResponseDTO> getAllSavingAccounts(){
		return accountService.getAllSavingAccounts();
	}
	
	@GetMapping("/display-all-current-accounts")
	public List<AccountResponseDTO> getAllCurrentAccounts(){
		return accountService.getAllCurrentAccounts();
	}

	
}

