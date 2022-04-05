package com.vipdsilva.app.ws.service;

import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.PeopleRepository;

public interface PeopleService {

	People createPeople(PeopleDtoRequestModel peopleDetails,  PeopleRepository repository);
	People updatePeople(Integer peopleId, UpdatePeopleRequestModel userDetails, PeopleRepository repository);
	void deletePeople(Integer peopleId, PeopleRepository repository);
}
