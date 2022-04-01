package com.vipdsilva.app.ws.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

	@GetMapping
	public String HelloPeople() {
		return "Hello People";
	}
}
