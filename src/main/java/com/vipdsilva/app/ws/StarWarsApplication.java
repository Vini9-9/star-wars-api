package com.vipdsilva.app.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.repository.PeopleRepository;

@SpringBootApplication
public class StarWarsApplication implements CommandLineRunner{
	
	private final PeopleRepository repository;
	
	public StarWarsApplication (PeopleRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(StarWarsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		People people = new People();
		people.setName("Luke");
		people.setHeight(175);
		people.setMass(70);
		
		repository.save(people);
		
	}
	
	

}
