package com.vipdsilva.app.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vipdsilva.app.ws.entities.Films;


public interface FilmsRepository extends JpaRepository <Films, Integer> {

}
