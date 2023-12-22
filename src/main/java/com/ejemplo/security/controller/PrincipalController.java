package com.ejemplo.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PrincipalController {
	
	
	
	
	
	@GetMapping("hello")
	public String hello(){
		return "Hello word";
	}

}
