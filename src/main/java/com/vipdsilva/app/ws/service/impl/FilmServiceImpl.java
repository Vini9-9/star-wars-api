package com.vipdsilva.app.ws.service.impl;

import java.time.Instant;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vipdsilva.app.ws.entities.Films;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.FilmDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateFilmRequestModel;
import com.vipdsilva.app.ws.model.response.FilmDtoResponseModel;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService {

	@Override
	public FilmDtoResponseModel createFilm(FilmDtoRequestModel FilmReq, FilmsRepository filmsRepository,
	 PeopleRepository peopleRepository) {
		
		Films film = new Films(FilmReq, peopleRepository);
	
		filmsRepository.save(film);
		FilmDtoResponseModel response = film.toResponseDto();
		
		return response;
	}

	@Override
	public FilmDtoResponseModel updateFilm(Integer FilmId, UpdateFilmRequestModel userReq,
			FilmsRepository filmsRepository, PeopleRepository peopleRepository) {

		Films FilmUpdated = filmsRepository.findById(FilmId).get();
		
		FilmUpdated.setEdited(Instant.now());
		
		Integer episode_idReq = userReq.getEpisode_id();
		String titleReq = userReq.getTitle();
		String opening_crawlReq = userReq.getOpening_crawl();
		String directorReq = userReq.getDirector();
		String producerReq = userReq.getProducer();
		String release_dateReq = userReq.getRelease_date();
		Set<String> characterReq = userReq.getCharacters();
		
		
		if (episode_idReq != null && !episode_idReq.toString().isBlank()) FilmUpdated.setTitle(titleReq);
		if (titleReq != null && !titleReq.isBlank()) FilmUpdated.setTitle(titleReq);
		if (opening_crawlReq != null && !opening_crawlReq.isBlank()) FilmUpdated.setOpening_crawl(opening_crawlReq);
		if (directorReq != null && !directorReq.isBlank()) FilmUpdated.setDirector(directorReq);
		if (producerReq != null && !producerReq.isBlank()) FilmUpdated.setProducer(producerReq);
		if (release_dateReq != null && !release_dateReq.isBlank()) FilmUpdated.setRelease_date(release_dateReq);

		if(characterReq != null) {
				
			Iterator<String> characterAsIterator = characterReq.iterator();
					
			if(characterAsIterator.hasNext()) {			
				FilmUpdated.clearCharacters();
				while (characterAsIterator.hasNext()){
					
					People person = peopleRepository.findByName(characterAsIterator.next().toString());
					FilmUpdated.setCharacter(person);

				}
			}
		}

		
		FilmDtoResponseModel response = FilmUpdated.toResponseDto();
		
		return response;
		
	}


	@Override
	public void deleteFilm(Integer FilmId, FilmsRepository filmsRepository) {
		
		Films filmDeleted = filmsRepository.findById(FilmId).get();

		filmDeleted.clearCharacters();
		filmsRepository.deleteById(FilmId);
		
	}
	
	

}
