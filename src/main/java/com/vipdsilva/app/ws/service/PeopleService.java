package com.vipdsilva.app.ws.service;


import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.repository.GenderRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;

public interface PeopleService {

	PeopleDtoResponseModel createPeople(PeopleDtoRequestModel peopleDetails,  PeopleRepository peopleRepository,
			GenderRepository genderRepository, ColorsRepository colorsRepository);
	
	PeopleDtoResponseModel updatePeople(Integer peopleId, UpdatePeopleRequestModel userDetails,
			PeopleRepository peopleRepository, GenderRepository genderRepository, ColorsRepository colorsRepository);
	
	void deletePeople(Integer peopleId, PeopleRepository repository);
}
