package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.enumeration.AccountType;
import com.example.demo.enumeration.Role;
import com.example.demo.model.AccountTypeConfig;
import com.example.demo.model.User;
import com.example.demo.repository.AccountTypeConfigRepository;
import com.example.demo.repository.UserRepository;

@SpringBootApplication
@Configuration
public class KeyStoneBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyStoneBankingApplication.class, args);
		System.out.println("Done");
	}


	@Bean
	CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	    return args -> {

	        if (userRepository.findByName("admin") == null) {

	            User admin = new User();
	            admin.setName("admin");
	            admin.setEmail("admin@gmail.com");
	            admin.setPassword(passwordEncoder.encode("admin123"));
	            admin.setRole(Role.ADMIN);

	            userRepository.save(admin);

	            System.out.println("Admin user created");
	        }
	    };
	}
    
    @Bean
	CommandLineRunner setDefaultConfig(AccountTypeConfigRepository repo) {
		return args -> {

			// SAVING ACCOUNT CONFIG
			if(repo.findById(AccountType.SAVING).isEmpty()) {

				AccountTypeConfig saving = new AccountTypeConfig();
				saving.setAccountType(AccountType.SAVING);
				saving.setMIN_BALANCE(1000.0);
				saving.setWITHDRAW_LIMIT(20000.0);

				repo.save(saving);
			}

			// CURRENT ACCOUNT CONFIG
			if(repo.findById(AccountType.CURRENT).isEmpty()) {

				AccountTypeConfig current = new AccountTypeConfig();
				current.setAccountType(AccountType.CURRENT);
				current.setMIN_BALANCE(0.0);
				current.setWITHDRAW_LIMIT(null); // no limit

				repo.save(current);
			}

			System.out.println("Default Account Configurations Loaded");
		};
	}
	
}