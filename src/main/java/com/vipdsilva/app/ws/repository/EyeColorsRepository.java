package com.vipdsilva.app.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vipdsilva.app.ws.entities.EyeColor;
import com.vipdsilva.app.ws.entities.People;


public interface EyeColorsRepository extends JpaRepository <EyeColor, Integer> {
	
	void deleteByPeople(People person);

}
