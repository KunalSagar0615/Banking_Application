package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.SavingAccount;
import com.example.demo.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@PostMapping("/create-saving-account")
	public void createSavingAccount(@RequestBody SavingAccount savingAccount) {
	    accountService.createAccount(savingAccount);
//	    System.out.println(savingAccount);
	}

	@PostMapping("/create-current-account")
	public void createCurrentAccount(@RequestBody CurrentAccount currentAccount) {
	    accountService.createAccount(currentAccount);
	}

	
}

