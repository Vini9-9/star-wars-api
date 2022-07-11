package com.vipdsilva.app.ws.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.vipdsilva.app.ws.ApplicationConfigTest;
import com.vipdsilva.app.ws.entities.Films;
import com.vipdsilva.app.ws.exceptions.NotFoundFilmException;
import com.vipdsilva.app.ws.model.request.FilmDtoRequestModel;
import com.vipdsilva.app.ws.model.response.FilmDtoResponseModel;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.impl.FilmServiceImpl;

public class FilmServiceTest extends ApplicationConfigTest {
	
	@Mock
	private FilmsRepository filmsRepository;

	@Mock
	private PeopleRepository peopleRepository;
    
	@Mock
	private FilmDtoRequestModel filmInfo;

	@Captor
	private ArgumentCaptor<Films> captorFilm;

    @Autowired
    private FilmServiceImpl filmService;
	
    @BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
        this.filmService = new FilmServiceImpl();
	}

	@Test
	void ShouldCreateANewFilm() {
        FilmDtoRequestModel filmInfo = generateFilmInfo();
        String titleReqFilm = filmInfo.getTitle();

        Mockito
		.when(filmsRepository.findByTitle(ArgumentMatchers.eq(titleReqFilm)))
        .thenReturn(null);

        FilmDtoResponseModel response = filmService.createFilm(filmInfo,
		 filmsRepository, peopleRepository);

		Mockito.verify(filmsRepository).save(captorFilm.capture());

		Films film = captorFilm.getValue();

		assertEquals(titleReqFilm, film.getTitle());
		assertEquals(response.getTitle(), film.toResponseDto().getTitle());
		
	}

	 @Test
	void ShouldNotCreateAFilmWithAnEquivalentTitle() {
        FilmDtoRequestModel filmInfo = generateFilmInfo();
        String titleReqFilm = filmInfo.getTitle();

		Mockito
		.when(filmsRepository.findByTitle(ArgumentMatchers.eq(titleReqFilm)))
        .thenReturn(new Films(filmInfo, peopleRepository));

		try {
			filmService.createFilm(filmInfo, filmsRepository, peopleRepository);
			Mockito.verifyNoInteractions(filmsRepository);
			Mockito.verifyNoInteractions(peopleRepository);
		} catch (Exception e) {
			assertEquals("Já possui um filme com esse title", e.getMessage());
		}
		
	}

	@Test
	void ShouldUpdateAFilmTitle() {
		String titleFilmToUpdate = "Filme do Han Solo";
		FilmDtoRequestModel userDetails = generateFilmInfo();
		userDetails.setTitle(titleFilmToUpdate);
		
		Optional<Films> optFilm = Optional.of(new Films(generateFilmInfo(), peopleRepository));
        Integer filmId = optFilm.get().getId();

		Mockito
		.when(filmsRepository.findById(ArgumentMatchers.eq(filmId)))
        .thenReturn(optFilm);

		Mockito
		.when(filmsRepository.findByTitle(ArgumentMatchers.eq(titleFilmToUpdate)))
        .thenReturn(null);

		FilmDtoResponseModel response = filmService.updateFilm(filmId, userDetails,
		 filmsRepository, peopleRepository);

		assertEquals(titleFilmToUpdate, response.getTitle());
		
	}

	@Test
	void ShouldNotFindAFilmByInexistingId() {
        Integer filmId = 99;
		String titleFilmToUpdate = "Star wars";
		FilmDtoRequestModel userDetails = generateFilmInfo();
		userDetails.setTitle(titleFilmToUpdate);

		Optional<Films> emptyFilm = Optional.empty();

		Mockito
		.when(filmsRepository.findById(ArgumentMatchers.eq(filmId)))
        .thenReturn(emptyFilm);

		 try {
			filmService.updateFilm(filmId, userDetails, filmsRepository, peopleRepository);
			Mockito.verifyNoInteractions(emptyFilm);
			Mockito.verifyNoInteractions(filmsRepository);
			Mockito.verifyNoInteractions(peopleRepository);
		} catch (Exception e) {
			assertEquals("Filme com id " + filmId + " não localizado", e.getMessage());
		}
		
	}

	

	@Test
	void ShouldDeleteAFilm() {		
		Optional<Films> optFilm = Optional.of(new Films(generateFilmInfo(), peopleRepository));
        Integer filmId = optFilm.get().getId();

		Mockito
		.when(filmsRepository.findById(ArgumentMatchers.eq(filmId)))
        .thenReturn(optFilm);

		filmService.deleteFilm(filmId, filmsRepository);
		Mockito.verify(filmsRepository).deleteById(filmId);
		
	}

	@Test
	void ShouldNotDeleteAInexistingFIlm() {
        Integer filmId = 99;

		Mockito
		.doThrow(new NotFoundFilmException(filmId))
		.when(filmsRepository).deleteById(filmId);

		 try {
			filmService.deleteFilm(filmId, filmsRepository);
			Mockito.verifyNoInteractions(filmsRepository);
		} catch (Exception e) {
			assertEquals("Filme com id " + filmId + " não localizado", e.getMessage());
		}
		
	} 

	private FilmDtoRequestModel generateFilmInfo() {
		FilmDtoRequestModel filmInfo = new FilmDtoRequestModel();
		filmInfo.setTitle("Han Solo");
		filmInfo.setId(7);
		filmInfo.setRelease_date("1980-05-17");
		filmInfo.setProducer("Producer");
		filmInfo.setDirector("Director");
		return filmInfo;
	}
    
}
