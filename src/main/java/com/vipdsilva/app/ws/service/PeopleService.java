package com.vipdsilva.app.ws.service;

import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.GenderRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;

public interface PeopleService {

	PeopleDtoResponseModel createPeople(PeopleDtoRequestModel peopleDetails,  PeopleRepository peopleRepository,
			GenderRepository genderRepository);
	
	PeopleDtoResponseModel updatePeople(Integer peopleId, UpdatePeopleRequestModel userDetails, PeopleRepository peopleRepository,
			GenderRepository genderRepository);
	
	void deletePeople(Integer peopleId, PeopleRepository repository);
}
