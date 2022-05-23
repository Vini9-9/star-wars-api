package com.vipdsilva.app.ws.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.vipdsilva.app.ws.entities.Films;

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
class FilmsRepositoryTest {

	@Autowired
	FilmsRepository filmsRepository;

	// GET
	@Test
	public void shouldLoadAFilmByName() {
		String filmName = "Uma Nova Esperança";
		Films film = filmsRepository.findByTitle(filmName);
		assertNotNull(film);
		assertEquals(filmName, film.getTitle());
	}

	@Test
	public void shouldNotLoadAFilmThatDoesNotExist() {
		String filmName = "Filme que não existe";
		Films film = filmsRepository.findByTitle(filmName);
		assertNull(film);
	}

	// CREATE

	@Test
	public void shouldCreateNewFilm() {
		String filmName = "Han Solo";

		Films film = new Films();
		film.setTitle(filmName);

		filmsRepository.save(film);

		Films filmCreated = filmsRepository.findByTitle(filmName);
		
		assertNotNull(filmCreated);
		assertEquals(filmName, filmCreated.getTitle());
	}


	// DELETE

	@Test
	public void shouldDeleteFilm() {
		String filmName = "Ataque dos Clones";

		Films film = filmsRepository.findByTitle(filmName);
		filmsRepository.delete(film);

		Films deletedFilm = filmsRepository.findByTitle(filmName);
		
		assertNull(deletedFilm);
	}

	// UPDATE

	@Test
	public void shouldUpdateFilm() {
		String filmName = "Uma Nova Esperança";
		String filmNameUpdated = "Nova Esperança";

		Films person = filmsRepository.findByTitle(filmName);
		person.setTitle(filmNameUpdated);

		Films updatedFilm = filmsRepository.findByTitle(filmNameUpdated);
		
		assertNotNull(updatedFilm);
		assertEquals(filmNameUpdated, updatedFilm.getTitle());
	}


	
}
