package com.vipdsilva.app.ws.service;

import com.vipdsilva.app.ws.model.request.FilmDtoRequestModel;
import com.vipdsilva.app.ws.model.response.FilmDtoResponseModel;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;

public interface FilmService {

	FilmDtoResponseModel createFilm(FilmDtoRequestModel FilmDetails,  FilmsRepository repository,
	PeopleRepository peopleRepository);
	FilmDtoResponseModel updateFilm(Integer FilmId, FilmDtoRequestModel userDetails,
	FilmsRepository filmsRepository, PeopleRepository peopleRepository);
	void deleteFilm(Integer FilmId, FilmsRepository repository);
}
