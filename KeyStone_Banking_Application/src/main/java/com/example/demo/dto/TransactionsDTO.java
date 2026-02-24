package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.enumeration.TransactionType;
import com.example.demo.model.Transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsDTO {

	private Long transitionId;
	private LocalDate transitionDate;
	private LocalTime transitionTime;
	private Double amount;
	private TransactionType transactionType;
	
	
	public static TransactionsDTO toTransactionsDTO(Transactions transaction) {
		TransactionsDTO dto=new TransactionsDTO();
		
		dto.setTransitionId(transaction.getTransitionId());
		dto.setTransitionDate(transaction.getTransitionDate());
		dto.setTransitionTime(transaction.getTransitionTime());
		dto.setAmount(transaction.getAmount());
		dto.setTransactionType(transaction.getTransactionType());
		
		
		return dto;
	}
}
