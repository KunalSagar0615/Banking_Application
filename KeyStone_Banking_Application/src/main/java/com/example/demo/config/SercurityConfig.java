package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration 
public class SercurityConfig {
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		http.csrf(csrf->csrf.disable())     //csrf is token  for session authentication
		.authorizeHttpRequests(auth-> auth
				.requestMatchers("/user/**").permitAll()
				.requestMatchers("/account/**").hasRole("EMPLOYEE")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults())
		.formLogin(Customizer.withDefaults());
		return http.build();
	}

//	
	@Bean
	PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	}
	
//	AuthenticationManager authenticateManager() {
//		return new authenprovid
//	}
}
