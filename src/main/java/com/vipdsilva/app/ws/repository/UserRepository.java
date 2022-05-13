package com.vipdsilva.app.ws.repository;

import java.util.Optional;

import com.vipdsilva.app.ws.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

	Optional<User> findByEmail(String email);
	
}