package com.vipdsilva.app.ws.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
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

	@Override
	public PeopleDtoResponseModel updatePeople(Integer peopleId, UpdatePeopleRequestModel userReq,
			PeopleRepository repository) {

		People peopleUpdated = repository.getById(peopleId);
		
		peopleUpdated.setEdited(Instant.now());
		
		String nameReq = userReq.getName();
		String birth_yearReq = userReq.getBirth_year();
		Integer heightReq = userReq.getHeight();
		Integer massReq = userReq.getMass();
		
		if (!nameReq.isBlank()) peopleUpdated.setName(nameReq);
		if(!birth_yearReq.isBlank()) peopleUpdated.setBirth_year(birth_yearReq);
		if (!heightReq.toString().isBlank()) peopleUpdated.setHeight(heightReq);
		if (!massReq.toString().isBlank()) peopleUpdated.setMass(massReq);
		
		
		PeopleDtoResponseModel response = peopleUpdated.toResponseDto();
		
		return response;
		
	}
	
	

}
