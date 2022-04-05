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
	public People createPeople(PeopleDtoRequestModel peopleReq, PeopleRepository repository) {
		
		People people = new People(peopleReq);
	
		repository.save(people);
		//PeopleDtoResponseModel response = people.toResponseDto();
		
		return people;
	}

	@Override
	public People updatePeople(Integer peopleId, UpdatePeopleRequestModel userReq,
			PeopleRepository repository) {

		People peopleUpdated = repository.findById(peopleId).get();
		
		peopleUpdated.setEdited(Instant.now());
		
		String nameReq = userReq.getName();
		String birth_yearReq = userReq.getBirth_year();
		Integer heightReq = userReq.getHeight();
		Integer massReq = userReq.getMass();
		
		if (nameReq != null && !nameReq.isBlank()) peopleUpdated.setName(nameReq);
		if (birth_yearReq != null && !birth_yearReq.isBlank()) peopleUpdated.setBirth_year(birth_yearReq);
		if (heightReq != null && !heightReq.toString().isBlank()) peopleUpdated.setHeight(heightReq);
		if (massReq != null && !massReq.toString().isBlank()) peopleUpdated.setMass(massReq);
		
		return peopleUpdated;
		
	}

	@Override
	public void deletePeople(Integer peopleId, PeopleRepository repository) {
		
		repository.deleteById(peopleId);
		
	}
	
	

}
