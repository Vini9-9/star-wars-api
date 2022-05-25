package com.vipdsilva.app.ws.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import com.vipdsilva.app.ws.entities.Films;
import com.vipdsilva.app.ws.exceptions.NotFoundFilmException;
import com.vipdsilva.app.ws.model.request.FilmDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateFilmRequestModel;
import com.vipdsilva.app.ws.model.response.FilmDtoResponseModel;
import com.vipdsilva.app.ws.model.response.WarningDtoResponseModel;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.FilmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

@RestController
@RequestMapping("/api/films")
public class FilmsController {

	// Injeção do Repository
	@Autowired
	private FilmsRepository filmsRepository;

	@Autowired
	private PeopleRepository peopleRepository;

	@Autowired
	FilmService filmService;

	@GetMapping("/all")
	public ResponseEntity<Page<FilmDtoResponseModel>> lista(
		@PageableDefault(sort = "releaseDate", page = 0, size = 10) Pageable paginacao
		) {

		Page<Films> filmes = filmsRepository.findAll(paginacao);
		
		if (filmes.isEmpty()) {
			
			throw new NotFoundFilmException();

		} else {

			Page<FilmDtoResponseModel> listaFilmesDto = FilmDtoResponseModel.converter(filmes);
			
			return new ResponseEntity<Page<FilmDtoResponseModel>>(listaFilmesDto, HttpStatus.OK);
		}

	}

	@GetMapping("/{filmId}")
	public ResponseEntity<FilmDtoResponseModel> showPerson(@PathVariable Integer filmId) {

		Optional<Films> film = filmsRepository.findById(filmId);

		if (film.isPresent()) {

			FilmDtoResponseModel response = film.get().toResponseDto();

			return new ResponseEntity<FilmDtoResponseModel>(response, HttpStatus.OK);

		} else {
			
			throw new NotFoundFilmException(filmId);
		}

	}

	@PostMapping
	public ResponseEntity<FilmDtoResponseModel> adicionar(
		@RequestBody FilmDtoRequestModel filmsDetails) {

		FilmDtoResponseModel returnValue = filmService.createFilm(filmsDetails, filmsRepository,
		peopleRepository);

		return new ResponseEntity<FilmDtoResponseModel>(returnValue, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{filmsId}")
	@Transactional
	public ResponseEntity<FilmDtoResponseModel> atualiza(@PathVariable Integer filmsId,
			@RequestBody UpdateFilmRequestModel body) {

		FilmDtoResponseModel returnValue = filmService.updateFilm(filmsId, body, 
		filmsRepository, peopleRepository);

		return new ResponseEntity<FilmDtoResponseModel>(returnValue, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{filmId}")
	@Transactional
	public ResponseEntity<WarningDtoResponseModel> deleta(@PathVariable Integer filmId) {


		Optional<Films> film = filmsRepository.findById(filmId);

		if (film.isPresent()) {

			filmService.deleteFilm(filmId, filmsRepository);
			
			WarningDtoResponseModel responseMsg = new WarningDtoResponseModel(
				"Filme", filmId);

			return new ResponseEntity<WarningDtoResponseModel>(responseMsg, HttpStatus.OK);

		} else {

			throw new NotFoundFilmException(filmId);

		}

	}

}
