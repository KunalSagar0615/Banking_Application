package com.example.demo.model;

import com.example.demo.enumeration.AccountType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountTypeConfig {

	@Id
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	private Double MIN_BALANCE;
	private Double WITHDRAW_LIMIT;
}
