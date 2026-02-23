package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AccountResponseDTO;
import com.example.demo.dto.BalanceDTO;
import com.example.demo.dto.UpdateAccountDTO;
import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.SavingAccount;
import com.example.demo.service.AccountService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



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

	@GetMapping("/check-account-balance/{acno}")
	public ResponseEntity<BalanceDTO> getBalance(@PathVariable Long acno) {
        BalanceDTO balanceDTO = accountService.getBalance(acno);
        return ResponseEntity.ok(balanceDTO);
    }
	
	@PutMapping("/withdraw-amount/{acno}/{amount}")
	public ResponseEntity<BalanceDTO> withdrawAmount(@PathVariable Long acno,@PathVariable Double amount) {
		BalanceDTO balanceDTO = accountService.withdrawAmount(acno, amount);
		return ResponseEntity.ok(balanceDTO);
	}
	
	@PutMapping("/update-account/{acno}")
	public ResponseEntity<UpdateAccountDTO> update(@PathVariable Long acno, @RequestBody UpdateAccountDTO updateAccountDTO) {
		UpdateAccountDTO dto = accountService.update(acno, updateAccountDTO);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/close-account/{acno}")
	public ResponseEntity<AccountResponseDTO> closeAccount(@PathVariable Long acno){
		AccountResponseDTO accountResponseDTO=accountService.closeAccount(acno);
		return ResponseEntity.ok(accountResponseDTO);
	}
	
	@GetMapping("/search-by-account-number/{acno}")
	public ResponseEntity<Account> getByAccountNumber(@PathVariable Long acno) {
		return ResponseEntity.ok(accountService.getByAccountNumber(acno));
	}
	
	@GetMapping("/search-by-email/{email}")
	public ResponseEntity<Account> getByEmail(@PathVariable String email) {
		return ResponseEntity.ok(accountService.getByEmail(email));
	}
	
	@GetMapping("/search-by-mobile/{mobile}")
	public ResponseEntity<Account> getByMobile(@PathVariable String mobile) {
		return ResponseEntity.ok(accountService.getByMobile(mobile));
	}
	
}

