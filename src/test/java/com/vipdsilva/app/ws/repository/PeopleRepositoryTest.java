package com.vipdsilva.app.ws.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.vipdsilva.app.ws.entities.People;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class PeopleRepositoryTest {

	@Autowired
	PeopleRepository peopleRepository;

	// GET
	@Test
	public void shouldLoadAPersonByName() {
		String personName = "R2-D2";
		People person = peopleRepository.findByName(personName);
		assertNotNull(person);
		assertEquals(personName, person.getName());
	}

	@Test
	public void shouldNotLoadAPersonThatDoesNotExist() {
		String personName = "Neymar";
		People person = peopleRepository.findByName(personName);
		assertNull(person);
	}

	// CREATE

	@Test
	public void shouldCreateNewPerson() {
		String personName = "Beru Whitesun lars";

		People person = new People();
		person.setName(personName);

		peopleRepository.save(person);

		People personCreated = peopleRepository.findByName(personName);

		/* List<People> lp = peopleRepository.findAll();

		for (People aPerson : lp) {

			System.out.println("Name: " + aPerson.getName());
			
		} */
		
		assertNotNull(personCreated);
		assertEquals(personName, personCreated.getName());
	}


	// DELETE

	@Test
	public void shouldDeletePerson() {
		String personName = "C-3PO";

		People person = peopleRepository.findByName(personName);
		peopleRepository.delete(person);

		People deletedPerson = peopleRepository.findByName(personName);
		
		assertNull(deletedPerson);
	}

	// UPDATE

	@Test
	public void shouldUpdatePerson() {
		String personName = "Leia Organa";
		String personNameUpdated = "Leia";

		People person = peopleRepository.findByName(personName);
		person.setName(personNameUpdated);

		People updatedPerson = peopleRepository.findByName(personNameUpdated);
		
		assertNotNull(updatedPerson);
		assertEquals(personNameUpdated, updatedPerson.getName());
	}


	
}
