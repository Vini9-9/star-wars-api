package com.vipdsilva.app.ws.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.PeopleService;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
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
	
	@GetMapping("/{peopleId}")
	public ResponseEntity<PeopleDtoResponseModel> showPerson(@PathVariable Integer peopleId) {
		People person = peopleRepository.getById(peopleId);
		PeopleDtoResponseModel response = person.toResponseDto();
		
		return new ResponseEntity<PeopleDtoResponseModel>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PeopleDtoResponseModel> adicionar (@RequestBody PeopleDtoRequestModel peopleDetails) {
		
		PeopleDtoResponseModel returnValue = peopleService.createPeople(peopleDetails, peopleRepository);
		
		return new ResponseEntity<PeopleDtoResponseModel>(returnValue, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{peopleId}")
	@Transactional
	public ResponseEntity<PeopleDtoResponseModel> updateUser(@PathVariable Integer peopleId, @RequestBody UpdatePeopleRequestModel body) {
		
		PeopleDtoResponseModel returnValue = peopleService.updatePeople(peopleId, body, peopleRepository);

		return new ResponseEntity<PeopleDtoResponseModel>(returnValue, HttpStatus.OK);
	}
	
	
}
