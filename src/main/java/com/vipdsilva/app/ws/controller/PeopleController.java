package com.vipdsilva.app.ws.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.vipdsilva.app.ws.exceptions.NotFoundException;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.DeleteDtoResponseModel;
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

		if (pessoas.isEmpty()) {
			
			throw new NotFoundException("Nenhuma pessoa cadastrada");

		} else {
			
			return new ResponseEntity<List<People>>(pessoas, HttpStatus.OK);
		}

	}

	@GetMapping("/{peopleId}")
	public ResponseEntity<PeopleDtoResponseModel> showPerson(@PathVariable Integer peopleId) {

		Optional<People> person = peopleRepository.findById(peopleId);

		if (person.isPresent()) {

			PeopleDtoResponseModel response = person.get().toResponseDto();

			return new ResponseEntity<PeopleDtoResponseModel>(response, HttpStatus.OK);

		} else {
			
			throw new NotFoundException("Pessoa com id " + peopleId + " não localizada");
		}

	}

	@PostMapping
	public ResponseEntity<PeopleDtoResponseModel> adicionar(@RequestBody PeopleDtoRequestModel peopleDetails) {

		PeopleDtoResponseModel returnValue = peopleService.createPeople(peopleDetails, peopleRepository);

		return new ResponseEntity<PeopleDtoResponseModel>(returnValue, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{peopleId}")
	@Transactional
	public ResponseEntity<PeopleDtoResponseModel> atualiza(@PathVariable Integer peopleId,
			@RequestBody UpdatePeopleRequestModel body) {

		PeopleDtoResponseModel returnValue = peopleService.updatePeople(peopleId, body, peopleRepository);

		return new ResponseEntity<PeopleDtoResponseModel>(returnValue, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{peopleId}")
	@Transactional
	public ResponseEntity<DeleteDtoResponseModel> deleta(@PathVariable Integer peopleId) {


		Optional<People> person = peopleRepository.findById(peopleId);

		if (person.isPresent()) {

			peopleService.deletePeople(peopleId, peopleRepository);
			
			DeleteDtoResponseModel responseMsg = new DeleteDtoResponseModel("peopleID: " + peopleId + " deletado com sucesso");

			return new ResponseEntity<DeleteDtoResponseModel>(responseMsg, HttpStatus.OK);

		} else {

			throw new NotFoundException("peopleID: " + peopleId + " não localizado");

		}

	}

}
