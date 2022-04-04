package com.vipdsilva.app.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vipdsilva.app.ws.entities.People;


public interface PeopleRepository extends JpaRepository <People, Integer> {

}
