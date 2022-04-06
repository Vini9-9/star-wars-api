package com.vipdsilva.app.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vipdsilva.app.ws.entities.Colors;


public interface ColorsRepository extends JpaRepository <Colors, Integer> {

	Colors findByName(String name);

}
