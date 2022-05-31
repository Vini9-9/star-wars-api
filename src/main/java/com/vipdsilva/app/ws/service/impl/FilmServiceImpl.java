package com.vipdsilva.app.ws.service.impl;

import java.time.Instant;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vipdsilva.app.ws.entities.Films;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.exceptions.AlreadyExistsFilmException;
import com.vipdsilva.app.ws.exceptions.NotFoundFilmException;
import com.vipdsilva.app.ws.model.request.FilmDtoRequestModel;
import com.vipdsilva.app.ws.model.response.FilmDtoResponseModel;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService {

	@Override
	public FilmDtoResponseModel createFilm(FilmDtoRequestModel FilmReq, 
	 FilmsRepository filmsRepository, PeopleRepository peopleRepository) {
		
		String titleName = FilmReq.getTitle();

		if(!hasFilm(titleName, filmsRepository)){

			Films film = new Films(FilmReq, peopleRepository);
			
			filmsRepository.save(film);
			FilmDtoResponseModel response = film.toResponseDto();
			
			return response;
		}
	
		throw new AlreadyExistsFilmException("title");
		
	}

	@Override
	public FilmDtoResponseModel updateFilm(Integer filmId, FilmDtoRequestModel userReq,
			FilmsRepository filmsRepository, PeopleRepository peopleRepository) {

		Optional<Films> optFilm = filmsRepository.findById(filmId);

        if(optFilm.isPresent()){

			Films filmUpdated = optFilm.get();
			filmUpdated.setEdited(Instant.now());
			
			Integer episode_idReq = userReq.getEpisode_id();
			String titleReq = userReq.getTitle();
			String opening_crawlReq = userReq.getOpening_crawl();
			String directorReq = userReq.getDirector();
			String producerReq = userReq.getProducer();
			String release_dateReq = userReq.getRelease_date();
			Set<String> characterReq = userReq.getCharacters();
			
			if (episode_idReq != null && !episode_idReq.toString().isBlank()) filmUpdated.setTitle(titleReq);
			if (titleReq != null && !titleReq.isBlank()) filmUpdated.setTitle(titleReq);
			if (opening_crawlReq != null && !opening_crawlReq.isBlank()) filmUpdated.setOpening_crawl(opening_crawlReq);
			if (directorReq != null && !directorReq.isBlank()) filmUpdated.setDirector(directorReq);
			if (producerReq != null && !producerReq.isBlank()) filmUpdated.setProducer(producerReq);
			if (release_dateReq != null && !release_dateReq.isBlank()) filmUpdated.setRelease_date(release_dateReq);

			if(characterReq != null) {
					
				Iterator<String> characterAsIterator = characterReq.iterator();
						
				if(characterAsIterator.hasNext()) {			
					filmUpdated.clearCharacters();
					while (characterAsIterator.hasNext()){
						People person = peopleRepository.findByName(characterAsIterator.next().toString());
						filmUpdated.setCharacter(person);
					}
				}
			}

			FilmDtoResponseModel response = filmUpdated.toResponseDto();
			return response;

		} else {
			throw new NotFoundFilmException(filmId);
		}
		
	}


	@Override
	public void deleteFilm(Integer filmId, FilmsRepository filmsRepository) {
		
		Optional<Films> filmDeleted = filmsRepository.findById(filmId);

		if(filmDeleted.isPresent()){
            filmDeleted.get().clearCharacters();
			filmsRepository.deleteById(filmId);
        } else {
            throw new NotFoundFilmException(filmId);
        }

	}
	
	public boolean hasFilm (String filmTitle, FilmsRepository filmsRepository) {
        return filmsRepository.findByTitle(filmTitle) != null;
    }

}
