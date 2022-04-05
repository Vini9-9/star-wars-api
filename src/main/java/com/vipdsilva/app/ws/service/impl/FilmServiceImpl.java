package com.vipdsilva.app.ws.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.vipdsilva.app.ws.entities.Films;
import com.vipdsilva.app.ws.model.request.FilmDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateFilmRequestModel;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService {

	@Override
	public Films createFilm(FilmDtoRequestModel FilmReq, FilmsRepository repository) {
		
		Films film = new Films(FilmReq);
	
		repository.save(film);
		//FilmDtoResponseModel response = Film.toResponseDto();
		
		return film;
	}

	@Override
	public Films updateFilm(Integer FilmId, UpdateFilmRequestModel userReq,
			FilmsRepository repository) {

		Films FilmUpdated = repository.findById(FilmId).get();
		
		FilmUpdated.setEdited(Instant.now());
		
		Integer episode_idReq = userReq.getEpisode_id();
		String titleReq = userReq.getTitle();
		String opening_crawlReq = userReq.getOpening_crawl();
		String directorReq = userReq.getDirector();
		String producerReq = userReq.getProducer();
		String release_dateReq = userReq.getRelease_date();
		
		
		if (episode_idReq != null && !episode_idReq.toString().isBlank()) FilmUpdated.setTitle(titleReq);
		if (titleReq != null && !titleReq.isBlank()) FilmUpdated.setTitle(titleReq);
		if (opening_crawlReq != null && !opening_crawlReq.isBlank()) FilmUpdated.setOpening_crawl(opening_crawlReq);
		if (directorReq != null && !directorReq.isBlank()) FilmUpdated.setDirector(directorReq);
		if (producerReq != null && !producerReq.isBlank()) FilmUpdated.setProducer(producerReq);
		if (release_dateReq != null && !release_dateReq.isBlank()) FilmUpdated.setRelease_date(release_dateReq);
		
		//FilmDtoResponseModel response = FilmUpdated.toResponseDto();
		
		return FilmUpdated;
		
	}


	@Override
	public void deleteFilm(Integer FilmId, FilmsRepository repository) {
		
		repository.deleteById(FilmId);
		
	}
	
	

}
