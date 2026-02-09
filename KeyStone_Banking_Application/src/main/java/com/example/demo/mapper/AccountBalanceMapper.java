package com.example.demo.mapper;

import com.example.demo.dto.BalanceDTO;
import com.example.demo.model.Account;

public class AccountBalanceMapper {

	public static BalanceDTO toBalanceDTO(Account account) {
		
		return new BalanceDTO(account.getName(),account.getBalance());		
		
	}
}
