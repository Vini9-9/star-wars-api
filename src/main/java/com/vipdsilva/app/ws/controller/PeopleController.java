package com.vipdsilva.app.ws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.PeopleService;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;

@RestController
@RequestMapping("/api/people")
public class PeopleController {
	
	// Injeção do Repository
	@Autowired
	private PeopleRepository peopleRepository;
	
	@Autowired
	PeopleService peopleService;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<People>> lista() {
		List<People> pessoas = peopleRepository.findAll();
		
		return new ResponseEntity<>(pessoas, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PeopleDtoResponseModel> adicionar (@RequestBody PeopleDtoRequestModel peopleDetails) {
		
		PeopleDtoResponseModel returnValue = peopleService.createPeople(peopleDetails, peopleRepository);
		
		return new ResponseEntity<PeopleDtoResponseModel>(returnValue, HttpStatus.CREATED);
	}
	
	
}
