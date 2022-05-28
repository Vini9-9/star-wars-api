package com.vipdsilva.app.ws.service.impl;

import java.time.Instant;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vipdsilva.app.ws.entities.Colors;
import com.vipdsilva.app.ws.entities.EyeColor;
import com.vipdsilva.app.ws.entities.Films;
import com.vipdsilva.app.ws.entities.Gender;
import com.vipdsilva.app.ws.entities.HairColor;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.entities.SkinColor;
import com.vipdsilva.app.ws.exceptions.AlreadyExistsPersonException;
import com.vipdsilva.app.ws.exceptions.NotFoundPeopleException;
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
	public PeopleDtoResponseModel createPeople(PeopleDtoRequestModel peopleReq,
	 PeopleRepository peopleRepository, GenderRepository genderRepository,
	 ColorsRepository colorsRepository, FilmsRepository filmsRepository) {

		String personName = peopleReq.getName();
		
		if(!hasPerson(personName, peopleRepository)){
			People people = new People(peopleReq, genderRepository, colorsRepository,
			filmsRepository);

			peopleRepository.save(people);
			PeopleDtoResponseModel response = people.toResponseDto();

			return response;
		}
    
        throw new AlreadyExistsPersonException("nome");
	}

	@Override
	public PeopleDtoResponseModel updatePeople(Integer peopleId, UpdatePeopleRequestModel userReq,
	 PeopleRepository peopleRepository, GenderRepository genderRepository,
	 ColorsRepository colorsRepository, FilmsRepository filmsRepository) {

		Optional<People> optPeople = peopleRepository.findById(peopleId);

        if(optPeople.isPresent()){

			People peopleUpdated = optPeople.get();

			if(hasPerson(userReq.getName(), peopleRepository)){
                throw new AlreadyExistsPersonException("nome");
            }

			peopleUpdated.setEdited(Instant.now());

			String nameReq = userReq.getName();
			String birth_yearReq = userReq.getBirth_year();
			Integer heightReq = userReq.getHeight();
			Integer massReq = userReq.getMass();
			String genderReq = userReq.getGender();
			String eye_colorReq = userReq.getEye_color();
			String skin_colorReq = userReq.getSkin_color();
			String hair_colorReq = userReq.getHair_color();

			String[] arrEye_colorReq = {eye_colorReq};
			String[] arrSkin_colorReq = {skin_colorReq};
			String[] arrHair_colorReq = {hair_colorReq};

			if(eye_colorReq != null && eye_colorReq.contains(",")){
				arrEye_colorReq = eye_colorReq.split(",");
			}

			if(skin_colorReq != null && skin_colorReq.contains(",")){
				arrSkin_colorReq = skin_colorReq.split(",");
			}

			if(hair_colorReq != null && hair_colorReq.contains(",")){
				arrHair_colorReq = hair_colorReq.split(",");
			}

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
			
			if(arrEye_colorReq[0] != null) {
				
				peopleUpdated.clearEyeColor();			
				EyeColor corOlho = new EyeColor();

				for (String eyeColor : arrEye_colorReq) {

					Colors color = colorsRepository.findByName(eyeColor.trim());
					corOlho.setColor(color);
		
					peopleUpdated.setEyeColor(corOlho);
				}
				
			}
			
			if(arrSkin_colorReq[0] != null) {

				peopleUpdated.clearSkinColor();			
				SkinColor corPele = new SkinColor();

				for (String skinColor : arrSkin_colorReq) {

					Colors color = colorsRepository.findByName(skinColor.trim());
					corPele.setColor(color);
		
					peopleUpdated.setSkinColor(corPele);
				}
			
			}
			
			if(arrHair_colorReq[0] != null) {

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

        } else {
            throw new NotFoundPeopleException(peopleId);
        }

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

	public boolean hasPerson (String personName, PeopleRepository peopleRepository) {
        return peopleRepository.findByName(personName) != null;
    }

}
