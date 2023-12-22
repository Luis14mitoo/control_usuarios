package com.ejemplo.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ejemplo.security.models.ERole;
import com.ejemplo.security.models.RoleEntity;
import com.ejemplo.security.models.UserEntity;
import com.ejemplo.security.repository.UserRepository;





@SpringBootApplication
public class SecurityApplication {

	
	@Value("${usuario.name}")
	String name;
	@Value("${usuario.password}")
	String password;
	@Value("${usuario.email}")
	String email;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	

	@Bean
	CommandLineRunner init(){
		return args -> {

			UserEntity userEntity = UserEntity.builder()
					.email(email)
					.username(name)
					.password(passwordEncoder.encode(password))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			

			userRepository.save(userEntity);
		};
	}

}
