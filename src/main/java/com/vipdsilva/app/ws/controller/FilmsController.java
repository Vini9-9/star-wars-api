package com.vipdsilva.app.ws.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.service.FilmService;


import com.vipdsilva.app.ws.entities.Films;
import com.vipdsilva.app.ws.exceptions.NotFoundException;
import com.vipdsilva.app.ws.model.request.FilmDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateFilmRequestModel;
import com.vipdsilva.app.ws.model.response.DeleteDtoResponseModel;

@RestController
@RequestMapping("/api/films")
public class FilmsController {

	// Injeção do Repository
	@Autowired
	private FilmsRepository filmsRepository;

	@Autowired
	FilmService filmService;

	@GetMapping("/all")
	public ResponseEntity<List<Films>> lista() {

		List<Films> pessoas = filmsRepository.findAll();

		if (pessoas.isEmpty()) {
			
			throw new NotFoundException("Nenhum filme cadastrado");

		} else {
			
			return new ResponseEntity<List<Films>>(pessoas, HttpStatus.OK);
		}

	}

	@GetMapping("/{filmsId}")
	public ResponseEntity<Films> showPerson(@PathVariable Integer filmsId) {

		Optional<Films> film = filmsRepository.findById(filmsId);

		if (film.isPresent()) {

			Films response = film.get();

			return new ResponseEntity<Films>(response, HttpStatus.OK);

		} else {
			
			throw new NotFoundException("Filme com id " + filmsId + " não localizado");
		}

	}

	@PostMapping
	public ResponseEntity<Films> adicionar(@RequestBody FilmDtoRequestModel filmsDetails) {

		Films returnValue = filmService.createFilm(filmsDetails, filmsRepository);

		return new ResponseEntity<Films>(returnValue, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{filmsId}")
	@Transactional
	public ResponseEntity<Films> atualiza(@PathVariable Integer filmsId,
			@RequestBody UpdateFilmRequestModel body) {

		Films returnValue = filmService.updateFilm(filmsId, body, filmsRepository);

		return new ResponseEntity<Films>(returnValue, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{filmId}")
	@Transactional
	public ResponseEntity<DeleteDtoResponseModel> deleta(@PathVariable Integer filmId) {


		Optional<Films> film = filmsRepository.findById(filmId);

		if (film.isPresent()) {

			filmService.deleteFilm(filmId, filmsRepository);
			
			DeleteDtoResponseModel responseMsg = new DeleteDtoResponseModel("film com id " + filmId + " deletado com sucesso");

			return new ResponseEntity<DeleteDtoResponseModel>(responseMsg, HttpStatus.OK);

		} else {

			throw new NotFoundException("filme com id " + filmId + " não localizado");

		}

	}

}
