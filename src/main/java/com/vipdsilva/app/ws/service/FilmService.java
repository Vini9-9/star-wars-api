package com.vipdsilva.app.ws.service;

import com.vipdsilva.app.ws.entities.Films;
import com.vipdsilva.app.ws.model.request.FilmDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateFilmRequestModel;
import com.vipdsilva.app.ws.repository.FilmsRepository;

public interface FilmService {

	Films createFilm(FilmDtoRequestModel FilmDetails,  FilmsRepository repository);
	Films updateFilm(Integer FilmId, UpdateFilmRequestModel userDetails, FilmsRepository repository);
	void deleteFilm(Integer FilmId, FilmsRepository repository);
}
