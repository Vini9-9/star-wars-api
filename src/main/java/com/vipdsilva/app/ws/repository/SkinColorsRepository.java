package com.vipdsilva.app.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.entities.SkinColor;


public interface SkinColorsRepository extends JpaRepository <SkinColor, Integer> {

	
	void deleteByPeople(People person);
}
