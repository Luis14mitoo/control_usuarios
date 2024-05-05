package com.ejemplo.security.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.security.DTO.CreateUserDTO;
import com.ejemplo.security.models.ERole;
import com.ejemplo.security.models.RoleEntity;
import com.ejemplo.security.models.UserEntity;
import com.ejemplo.security.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class PrincipalController {
	
	
	
	
	
	  	@Autowired
	    private PasswordEncoder passwordEncoder;

	    @Autowired
	    private UserRepository userRepository;

	    @GetMapping("/hello")
	    public String hello(){
	        return "Hello World Not Secured";
	    }

	    @GetMapping("/helloSecured")
	    public String helloSecured(){
	        return "Hello World Secured";
	    }

	    @PostMapping("/createUser")
	    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
	    	
	    	
	        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
	                .map(role -> RoleEntity.builder()
	                        .name(ERole.valueOf(role))
	                        .build())
	                .collect(Collectors.toSet());

	        UserEntity userEntity = UserEntity.builder()
	                .username(createUserDTO.getUsername())   
	                .password(passwordEncoder.encode(createUserDTO.getPassword()))
	                .email(createUserDTO.getEmail())
	                .roles(roles)
	                .build();

	        userRepository.save(userEntity);

	        return ResponseEntity.ok(userEntity);
	    }

	    
	    @GetMapping("/get_users")
	    public ResponseEntity<?> getUser(){
	    	
	    	return ResponseEntity.ok(userRepository.findAll());
	    	
	    }
	    
	    @GetMapping("/get_users/{id}")
	    public ResponseEntity<?> getUser(@PathVariable Long id){
	    	
	    	return ResponseEntity.ok(userRepository.findById(id));
	    	
	    }
	    
	    
	    @DeleteMapping("/deleteUser")
	    public String deleteUser(@RequestParam String id){
	        userRepository.deleteById(Long.parseLong(id));
	        return "Se ha borrado el user con id".concat(id);
	    }
}
