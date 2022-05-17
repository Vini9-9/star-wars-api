package com.vipdsilva.app.ws.service.impl;

import java.time.Instant;
import java.util.Iterator;
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

		People peopleUpdated = peopleRepository.findById(peopleId).get();

		peopleUpdated.setEdited(Instant.now());

		String nameReq = userReq.getName();
		String birth_yearReq = userReq.getBirth_year();
		Integer heightReq = userReq.getHeight();
		Integer massReq = userReq.getMass();
		String genderReq = userReq.getGender();
		String[] arrEye_colorReq = userReq.getEye_color().split(",");
		String[] arrSkin_colorReq = userReq.getSkin_color().split(",");
		String[] arrHair_colorReq = userReq.getHair_color().split(",");
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
			peopleUpdated.setGender(gender);
		}

		if(filmsReq != null) {
				
			Iterator<String> filmAsIterator = filmsReq.iterator();
					
			if(filmAsIterator.hasNext()) {			
				peopleUpdated.clearFilms();
				while (filmAsIterator.hasNext()){
					
					Films film = filmsRepository.findByTitle(filmAsIterator.next());
					peopleUpdated.setFilm(film);

				}
			}
		}
		
		if(arrEye_colorReq != null) {
			
			peopleUpdated.clearEyeColor();			
			EyeColor corOlho = new EyeColor();

			for (String eyeColor : arrEye_colorReq) {

				Colors color = colorsRepository.findByName(eyeColor.trim());
				corOlho.setColor(color);
	
				peopleUpdated.setEyeColor(corOlho);
			}
			
		}
		
		if(arrSkin_colorReq != null) {

			peopleUpdated.clearSkinColor();			
			SkinColor corPele = new SkinColor();

			for (String skinColor : arrSkin_colorReq) {

				Colors color = colorsRepository.findByName(skinColor.trim());
				corPele.setColor(color);
	
				peopleUpdated.setSkinColor(corPele);
			}
		
		}
		
		if(arrHair_colorReq != null) {

			peopleUpdated.clearHairColor();	
			HairColor corCabelo = new HairColor();

			for (String hairColor : arrHair_colorReq) {

				Colors color = colorsRepository.findByName(hairColor.trim());
				corCabelo.setColor(color);
	
				peopleUpdated.setHairColor(corCabelo);
			}
		
		}
		
		PeopleDtoResponseModel responseDto = peopleUpdated.toResponseDto();
		
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
