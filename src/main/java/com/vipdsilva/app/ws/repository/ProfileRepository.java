package com.vipdsilva.app.ws.repository;

import java.util.Optional;

import com.vipdsilva.app.ws.entities.Profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository <Profile, Long> {

	Optional<Profile> findByName(String name);
	
}