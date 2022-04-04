package com.vipdsilva.app.ws.service.impl;

import org.springframework.stereotype.Service;

import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.PeopleService;

@Service
public class PeopleServiceImpl implements PeopleService {

	@Override
	public PeopleDtoResponseModel createPeople(PeopleDtoRequestModel peopleReq, PeopleRepository repository) {
		
		People people = new People(
				peopleReq.getId(),
				peopleReq.getName(),
				peopleReq.getHeight(),
				peopleReq.getMass(),
				peopleReq.getBirth_year()
				);
	
		repository.save(people);
		PeopleDtoResponseModel response = people.toResponseDto();
		
		return response;
	}
	
	

}
