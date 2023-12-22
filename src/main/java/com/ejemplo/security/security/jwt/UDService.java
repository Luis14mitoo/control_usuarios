package com.ejemplo.security.security.jwt;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ejemplo.security.models.UserEntity;
import com.ejemplo.security.repository.UserRepository;

@Service
public class UDService implements UserDetailsService{

	
	@Autowired
	private UserRepository userReposistory;
	
		
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userEntity = userReposistory.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("El usuario "+username+" no existe."));
		
		 Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
	                .stream()
	                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))
	                .collect(Collectors.toSet());
	
		return  new User(userEntity.getUsername(),userEntity.getPassword(),
				true,
				true,
				true,
				true,
				authorities);
			
		
	
		
		
	}
	
	
	

}
