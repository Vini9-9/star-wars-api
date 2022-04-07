package com.vipdsilva.app.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vipdsilva.app.ws.entities.HairColor;
import com.vipdsilva.app.ws.entities.People;



public interface HairColorsRepository extends JpaRepository <HairColor, Integer> {

	void deleteByPeople(People person);
	
}
