package com.vipdsilva.app.ws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vipdsilva.app.ws.entities.People;

@Repository
public interface PeopleRepository extends CrudRepository<People, Integer> {

}
