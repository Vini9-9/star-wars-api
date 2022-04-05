package com.vipdsilva.app.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vipdsilva.app.ws.entities.Gender;


public interface GenderRepository extends JpaRepository <Gender, Integer> {

	Gender findByName(String name);

}
