package com.vipdsilva.app.ws.service;


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

public interface PeopleService {

	PeopleDtoResponseModel createPeople(PeopleDtoRequestModel peopleDetails,  PeopleRepository peopleRepository,
			GenderRepository genderRepository, ColorsRepository colorsRepository, FilmsRepository filmsRepository);
	
	PeopleDtoResponseModel updatePeople(Integer peopleId, UpdatePeopleRequestModel userDetails,
			PeopleRepository peopleRepository, GenderRepository genderRepository,
			ColorsRepository colorsRepository, FilmsRepository filmsRepository);
	
	void deletePeople(Integer peopleId, PeopleRepository repository, EyeColorsRepository eyeColorsRepository,
			 HairColorsRepository hairColorsRepository,SkinColorsRepository skinColorsRepository);
}
