package com.ejemplo.security.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class CorsConfig{

	//@Bean
	public WebMvcConfigurer corConfigurer(){
		
		return new WebMvcConfigurer(){
		
			public void addCorsMapings(CorsRegistry register){
				
				register.addMapping("/**")
				.allowedOrigins("http://localhost:3000")
				.allowedMethods("*");	
		
			}
			
		};
		
		}
	
}
			
		
	





