package com.ejemplo.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.ejemplo.security.models.RoleEntity;

public interface RoleRepository  extends CrudRepository<RoleEntity, Long>{

}
