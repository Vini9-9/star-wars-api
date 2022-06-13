package com.vipdsilva.app.ws.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.vipdsilva.app.ws.entities.Colors;
import com.vipdsilva.app.ws.entities.Gender;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.repository.GenderRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.impl.PeopleServiceImpl;

public class PeopleServiceTest extends ApplicationConfigTest {
	
	@Mock
	private FilmsRepository filmsRepository;

	@Mock
	private PeopleRepository peopleRepository;

	@Mock
	private GenderRepository genderRepository;

	@Mock
	private ColorsRepository colorsRepository;
    
	@Mock
	private PeopleDtoRequestModel peopleInfo;

	@Captor
	private ArgumentCaptor<People> captorPeople;

    @Autowired
    private PeopleServiceImpl peopleService;
	
    @BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
        this.peopleService = new PeopleServiceImpl();
	}

	@Test
	void ShouldCreateANewPeople() {
        PeopleDtoRequestModel filmInfo = generatePeopleInfo();
        String nameReqPeople = filmInfo.getName();
        String genderReqPeople = filmInfo.getGender();
        String eyeColorReqPeople = filmInfo.getEye_color();
        String hairColorReqPeople = filmInfo.getHair_color();
        String skinColorReqPeople = filmInfo.getSkin_color();

		Gender gender = new Gender(genderReqPeople);
		Colors eyeColor = new Colors(eyeColorReqPeople);
		Colors hairColor = new Colors(hairColorReqPeople);
		Colors skinColor = new Colors(skinColorReqPeople);

        Mockito
		.when(peopleRepository.findByName(ArgumentMatchers.eq(nameReqPeople)))
        .thenReturn(null);

		Mockito
		.when(genderRepository.findByName(ArgumentMatchers.eq(genderReqPeople)))
		.thenReturn(gender);
		
		Mockito
		.when(colorsRepository.findByName(ArgumentMatchers.eq(eyeColorReqPeople.trim())))
		.thenReturn(eyeColor);
		
		Mockito
		.when(colorsRepository.findByName(ArgumentMatchers.eq(hairColorReqPeople.trim())))
		.thenReturn(hairColor);
		
		Mockito
		.when(colorsRepository.findByName(ArgumentMatchers.eq(skinColorReqPeople.trim())))
		.thenReturn(skinColor);


        PeopleDtoResponseModel response = peopleService
		.createPeople(filmInfo, peopleRepository, genderRepository,
		 colorsRepository, filmsRepository);

		Mockito.verify(peopleRepository).save(captorPeople.capture());

		People person = captorPeople.getValue();

		assertEquals(nameReqPeople, person.getName());
		assertEquals(response.getName(), person.toResponseDto().getName());
		
	}

	/* @Test
	void ShouldNotCreateAFilmWithAnEquivalentTitle() {
        FilmDtoRequestModel filmInfo = generatePeopleInfo();
        String titleReqFilm = filmInfo.getTitle();

		Mockito
		.when(peopleRepository.findByName(ArgumentMatchers.eq(titleReqFilm)))
        .thenReturn(new Films(filmInfo, peopleRepository));

		try {
			peopleService.createPeople(filmInfo, peopleRepository, peopleRepository);
			Mockito.verifyNoInteractions(peopleRepository);
			Mockito.verifyNoInteractions(peopleRepository);
		} catch (Exception e) {
			assertEquals("Já possui um filme com esse title", e.getMessage());
		}
		
	}

	@Test
	void ShouldUpdateAFilmTitle() {
		String titleFilmToUpdate = "Filme do Han Solo";
		FilmDtoRequestModel userDetails = generatePeopleInfo();
		userDetails.setTitle(titleFilmToUpdate);
		
		Optional<Films> optFilm = Optional.of(new Films(generatePeopleInfo(), peopleRepository));
        Integer filmId = optFilm.get().getId();

		Mockito
		.when(peopleRepository.findById(ArgumentMatchers.eq(filmId)))
        .thenReturn(optFilm);

		Mockito
		.when(peopleRepository.findByName(ArgumentMatchers.eq(titleFilmToUpdate)))
        .thenReturn(null);

		FilmDtoResponseModel response = peopleService.updateFilm(filmId, userDetails,
		 peopleRepository, peopleRepository);

		assertEquals(titleFilmToUpdate, response.getTitle());
		
	}

	@Test
	void ShouldNotFindAFilmByInexistingId() {
        Integer filmId = 99;
		String titleFilmToUpdate = "Star wars";
		FilmDtoRequestModel userDetails = generatePeopleInfo();
		userDetails.setTitle(titleFilmToUpdate);

		Optional<Films> emptyFilm = Optional.empty();

		Mockito
		.when(peopleRepository.findById(ArgumentMatchers.eq(filmId)))
        .thenReturn(emptyFilm);

		 try {
			peopleService.updateFilm(filmId, userDetails, peopleRepository, peopleRepository);
			Mockito.verifyNoInteractions(emptyFilm);
			Mockito.verifyNoInteractions(peopleRepository);
			Mockito.verifyNoInteractions(peopleRepository);
		} catch (Exception e) {
			assertEquals("Filme com id " + filmId + " não localizado", e.getMessage());
		}
		
	}

	

	@Test
	void ShouldDeleteAFilm() {		
		Optional<Films> optFilm = Optional.of(new Films(generatePeopleInfo(), peopleRepository));
        Integer filmId = optFilm.get().getId();

		Mockito
		.when(peopleRepository.findById(ArgumentMatchers.eq(filmId)))
        .thenReturn(optFilm);

		peopleService.deleteFilm(filmId, peopleRepository);
		Mockito.verify(peopleRepository).deleteById(filmId);
		
	}

	@Test
	void ShouldNotDeleteAInexistingFIlm() {
        Integer filmId = 99;

		Mockito
		.doThrow(new NotFoundFilmException(filmId))
		.when(peopleRepository).deleteById(filmId);

		 try {
			peopleService.deletePeople(filmId, peopleRepository);
			Mockito.verifyNoInteractions(peopleRepository);
		} catch (Exception e) {
			assertEquals("Filme com id " + filmId + " não localizado", e.getMessage());
		}
		
	}  */

	private PeopleDtoRequestModel generatePeopleInfo() {
		PeopleDtoRequestModel peopleInfo = new PeopleDtoRequestModel();
		peopleInfo.setId(7);
		peopleInfo.setName("Han Solo");
		peopleInfo.setGender("masculino");
		peopleInfo.setMass(74);
		peopleInfo.setHeight(170);
		peopleInfo.setBirth_year("32BBY");
		peopleInfo.setEye_color("castanho escuro");
		peopleInfo.setHair_color("castanho claro");
		peopleInfo.setSkin_color("bege claro");
		return peopleInfo;
	}
    
}
