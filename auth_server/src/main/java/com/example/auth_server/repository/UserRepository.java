package com.example.auth_server.repository;

import com.example.auth_server.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{

	User findByUsername(String string);
    
}
