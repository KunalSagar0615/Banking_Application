package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.enumeration.AccountType;
import com.example.demo.model.AccountTypeConfig;

public interface AccountTypeConfigRepository extends JpaRepository<AccountTypeConfig, AccountType> {

}
