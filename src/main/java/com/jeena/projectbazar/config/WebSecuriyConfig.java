package com.asiya.projectbazar.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class WebSecuriyConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(authorize->authorize
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/user/**").hasRole("USER"))
		.authorizeHttpRequests(authorize->authorize
				.requestMatchers("/**")
				.permitAll()
				.anyRequest()
				.authenticated())
		.formLogin(login->login
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/welcome",true)
				.failureUrl("/login?failed")
				.permitAll())
		.logout(logout->logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.permitAll()
				.invalidateHttpSession(true));
		http.csrf().disable();
		return http.build();
		
		}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
		.passwordEncoder(passwordEncoder)
		.dataSource(dataSource)
		 .usersByUsernameQuery("SELECT username, password, enable FROM user WHERE username = ?")
	        .authoritiesByUsernameQuery(
	            "SELECT u.username, r.role " +
	            "FROM user AS u " +
	            "JOIN user_role AS r ON u.user_id = r.user_id " +
	            "WHERE u.username = ?"
	        );

	}

}
