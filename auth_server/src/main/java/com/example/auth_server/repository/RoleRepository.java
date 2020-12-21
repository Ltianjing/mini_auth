package com.example.auth_server.repository;

import com.example.auth_server.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String>{

	Role findByName(String name);
    
}
