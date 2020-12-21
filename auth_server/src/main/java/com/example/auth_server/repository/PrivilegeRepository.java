package com.example.auth_server.repository;

import com.example.auth_server.entity.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, String>{

	Privilege findByName(String name);
    
}
