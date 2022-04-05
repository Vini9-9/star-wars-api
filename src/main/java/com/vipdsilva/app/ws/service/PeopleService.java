package com.vipdsilva.app.ws.service;

import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.PeopleRepository;

public interface PeopleService {

	PeopleDtoResponseModel createPeople(PeopleDtoRequestModel peopleDetails,  PeopleRepository repository);
	PeopleDtoResponseModel updatePeople(Integer peopleId, UpdatePeopleRequestModel userDetails, PeopleRepository repository);
	void deletePeople(Integer peopleId, PeopleRepository repository);
}
