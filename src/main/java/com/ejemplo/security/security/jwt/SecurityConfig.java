package com.ejemplo.security.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private Util util;
	
	
	@Autowired
	private UDService udService;
	
	
	@Autowired
	JwtAuthorizationFilter authorizationfilter;
	

	@Bean
	SecurityFilterChain securityFilterchain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception{
		
		
		
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(util);
		
		
		jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		
		
		return httpSecurity
				.csrf(conf -> conf.disable())
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/hello").permitAll();
					auth.anyRequest().authenticated();
				})
				.sessionManagement(session -> {
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.addFilter(jwtAuthenticationFilter)
				.addFilterBefore(authorizationfilter, UsernamePasswordAuthenticationFilter.class)
				.build();				
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception{
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(udService)
				.passwordEncoder(passwordEncoder)
				.and().build();
	}
	
	
	
	
}
