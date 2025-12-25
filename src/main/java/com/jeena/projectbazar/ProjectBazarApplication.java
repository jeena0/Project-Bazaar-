package com.asiya.projectbazar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication

public class ProjectBazarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectBazarApplication.class, args);
	}
@Bean
public PasswordEncoder getPasswordEncoder() {
	PasswordEncoder encoder= new BCryptPasswordEncoder();
	return encoder;
	
}

	

}
