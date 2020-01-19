package com.shoppingdiak.repository;

import org.springframework.data.repository.CrudRepository;

import com.shoppingdiak.security.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
