package com.vipdsilva.app.ws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.entities.People;

@RestController
@RequestMapping("/api/people")
public class PeopleController {
	
	// Injeção do Repository
	@Autowired
	private PeopleRepository peopleRepository;
	
	
	@GetMapping("/all")
	public List<People> lista() {
		List<People> pessoas = peopleRepository.findAll();
		return pessoas;
	}
	
	
}
