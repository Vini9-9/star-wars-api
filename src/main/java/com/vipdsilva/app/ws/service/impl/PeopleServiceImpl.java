package com.vipdsilva.app.ws.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.vipdsilva.app.ws.entities.Gender;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.GenderRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.PeopleService;

@Service
public class PeopleServiceImpl implements PeopleService {

	@Override
	public PeopleDtoResponseModel createPeople(PeopleDtoRequestModel peopleReq, PeopleRepository repository,
			GenderRepository genderRepository) {
		
		People people = new People(peopleReq, genderRepository);
	
		repository.save(people);
		PeopleDtoResponseModel response = people.toResponseDto();
		
		return response;
	}

	@Override
	public PeopleDtoResponseModel updatePeople(Integer peopleId, UpdatePeopleRequestModel userReq,
			PeopleRepository peopleRepository, GenderRepository genderRepository) {

		People peopleUpdated = peopleRepository.findById(peopleId).get();
		
		peopleUpdated.setEdited(Instant.now());
		
		String nameReq = userReq.getName();
		String birth_yearReq = userReq.getBirth_year();
		Integer heightReq = userReq.getHeight();
		Integer massReq = userReq.getMass();
		String genderReq = userReq.getGender();
		
		if (nameReq != null && !nameReq.isBlank()) peopleUpdated.setName(nameReq);
		if (birth_yearReq != null && !birth_yearReq.isBlank()) peopleUpdated.setBirth_year(birth_yearReq);
		if (heightReq != null && !heightReq.toString().isBlank()) peopleUpdated.setHeight(heightReq);
		if (massReq != null && !massReq.toString().isBlank()) peopleUpdated.setMass(massReq);
		
		if (genderReq != null && !genderReq.isBlank()) { 
			Gender gender = genderRepository.findByName(genderReq);
			
			System.out.println(gender);
			
			peopleUpdated.setGender(gender);
		}
		
		PeopleDtoResponseModel response = peopleUpdated.toResponseDto();
		
		return response;
		
	}

	@Override
	public void deletePeople(Integer peopleId, PeopleRepository repository) {
		
		repository.deleteById(peopleId);
		
	}
	
	

}
