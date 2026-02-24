package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.enumeration.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account_Transitions")
public class Transactions {

	@Id
	private Long accountno;
	private LocalDate transitionDate;
	private LocalTime transitionTime;
	private Double amount;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	public Transactions(Long accno, Double amt, TransactionType type) {
		this.accountno = accno;
		this.amount = amt;
		this.transactionType = type;
	}
	
	@PrePersist
	public void onTransitionDone() {
		this.transitionDate=LocalDate.now();
		this.transitionTime=LocalTime.now();
	}
	
}
