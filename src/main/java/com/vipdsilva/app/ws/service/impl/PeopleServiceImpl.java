package com.vipdsilva.app.ws.service.impl;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vipdsilva.app.ws.entities.Colors;
import com.vipdsilva.app.ws.entities.EyeColor;
import com.vipdsilva.app.ws.entities.Films;
import com.vipdsilva.app.ws.entities.Gender;
import com.vipdsilva.app.ws.entities.HairColor;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.entities.SkinColor;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.repository.EyeColorsRepository;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.repository.GenderRepository;
import com.vipdsilva.app.ws.repository.HairColorsRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.repository.SkinColorsRepository;
import com.vipdsilva.app.ws.service.PeopleService;

@Service
public class PeopleServiceImpl implements PeopleService {

	@Override
	public PeopleDtoResponseModel createPeople(PeopleDtoRequestModel peopleReq, PeopleRepository peopleRepository,
			GenderRepository genderRepository, ColorsRepository colorsRepository,
			FilmsRepository filmsRepository) {

		People people = new People(peopleReq, genderRepository, colorsRepository, filmsRepository);

		peopleRepository.save(people);
		PeopleDtoResponseModel response = people.toResponseDto();

		return response;
	}

	@Override
	public PeopleDtoResponseModel updatePeople(Integer peopleId, UpdatePeopleRequestModel userReq,
			PeopleRepository peopleRepository, GenderRepository genderRepository,
			ColorsRepository colorsRepository, FilmsRepository filmsRepository) {

		// Integer peopleId = userReq.getId();

		People peopleUpdated = peopleRepository.findById(peopleId).get();

		peopleUpdated.setEdited(Instant.now());

		String nameReq = userReq.getName();
		String birth_yearReq = userReq.getBirth_year();
		Integer heightReq = userReq.getHeight();
		Integer massReq = userReq.getMass();
		String genderReq = userReq.getGender();
		Set<String> eye_colorReq = userReq.getEye_color();
		Set<String> skin_colorReq = userReq.getSkin_color();
		Set<String> hair_colorReq = userReq.getHair_color();
		Set<String> filmsReq = userReq.getFilms();

		if (nameReq != null && !nameReq.isBlank())
			peopleUpdated.setName(nameReq);
		if (birth_yearReq != null && !birth_yearReq.isBlank())
			peopleUpdated.setBirth_year(birth_yearReq);
		if (heightReq != null && !heightReq.toString().isBlank())
			peopleUpdated.setHeight(heightReq);
		if (massReq != null && !massReq.toString().isBlank())
			peopleUpdated.setMass(massReq);

		if (genderReq != null && !genderReq.isBlank()) {
			Gender gender = genderRepository.findByName(genderReq);

			System.out.println(gender);

			peopleUpdated.setGender(gender);
		}

		PeopleDtoResponseModel responseDto = peopleUpdated.toResponseDto();
		
//		if (eye_colorReq != null && !eye_colorReq.isEmpty()) {
//
//			EyeColor corOlho = new EyeColor();
//
//			for (int i = 0; i < eye_colorReq.size(); i++) {
//
//				Colors color = colorsRepository.findByName(eye_colorReq.get(i));
//				corOlho.setColor(color);
//
//				corOlho.setPeople(peopleUpdated);
//				responseDto.setEye_color(corOlho);
//
//			}
//
//		}
		
		if(eye_colorReq != null) {
			
			Iterator<String> eyeAsIterator = eye_colorReq.iterator();
			
			if(eyeAsIterator.hasNext()) {			
				EyeColor corOlho = new EyeColor();
				
				while (eyeAsIterator.hasNext()){
		        	
		        	Colors color = colorsRepository.findByName(eyeAsIterator.next());
		        	corOlho.setColor(color);

					corOlho.setPeople(peopleUpdated);
					responseDto.setEye_color(corOlho);

		        }
			}
			
		}
		
		if(skin_colorReq != null) {
		
			Iterator<String> skinAsIterator = skin_colorReq.iterator();
					
			if(skinAsIterator.hasNext()) {			
				SkinColor corPele = new SkinColor();
				
				while (skinAsIterator.hasNext()){
		        	
		        	Colors color = colorsRepository.findByName(skinAsIterator.next());
		        	corPele.setColor(color);
	
					corPele.setPeople(peopleUpdated);
					responseDto.setSkin_color(corPele);
	
		        }
			}
		}
		
		if(hair_colorReq != null) {
		
			Iterator<String> hairAsIterator = hair_colorReq.iterator();
			
			if(hairAsIterator.hasNext()) {			
				HairColor corCabelo = new HairColor();
				
				while (hairAsIterator.hasNext()){
		        	
		        	Colors color = colorsRepository.findByName(hairAsIterator.next());
		        	corCabelo.setColor(color);
	
					corCabelo.setPeople(peopleUpdated);
					responseDto.setHair_color(corCabelo);
	
		        }
			}
		
		}
		
		if(filmsReq != null) {
		
			Iterator<String> filmAsIterator = filmsReq.iterator();
					
			if(filmAsIterator.hasNext()) {			
				
				while (filmAsIterator.hasNext()){
		        	
					Films film = filmsRepository.findByTitle(filmAsIterator.next());
					peopleUpdated.setFilm(film);
					responseDto.setFilm(film);
	
		        }
			}
		}
		
		return responseDto;

	}

	@Override
	public void deletePeople(Integer peopleId, PeopleRepository peopleRepository, EyeColorsRepository eyeColorsRepository,
			 HairColorsRepository hairColorsRepository,SkinColorsRepository skinColorsRepository) {

		People personDeleted = peopleRepository.findById(peopleId).get();
		
		eyeColorsRepository.deleteByPeople(personDeleted);
		hairColorsRepository.deleteByPeople(personDeleted);
		skinColorsRepository.deleteByPeople(personDeleted);
		personDeleted.clearFilms();

		peopleRepository.deleteById(peopleId);

	}

}
