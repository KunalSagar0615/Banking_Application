package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.security.auth.message.config.AuthConfig;

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

	@Bean
	PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	}
	
//	@Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
}
