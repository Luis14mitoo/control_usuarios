package com.ejemplo.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ejemplo.security.models.UserEntity;

public interface UserRepository extends CrudRepository <UserEntity, Long> {
	
	 	Optional<UserEntity> findByUsername(String username);

	    @Query("select u from UserEntity u where u.username = ?1")
	    Optional<UserEntity> getName(String username);

}
