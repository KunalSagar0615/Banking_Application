package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.TransactionsDTO;
import com.example.demo.model.Transactions;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, String>{
	List<Transactions> findByAccountno(Long accountNo);
	
}
