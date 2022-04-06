package com.vipdsilva.app.ws.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vipdsilva.app.ws.entities.Colors;
import com.vipdsilva.app.ws.entities.EyeColor;
import com.vipdsilva.app.ws.entities.Gender;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.repository.GenderRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.service.PeopleService;

@Service
public class PeopleServiceImpl implements PeopleService {

	@Override
	public PeopleDtoResponseModel createPeople(PeopleDtoRequestModel peopleReq, PeopleRepository repository,
			GenderRepository genderRepository, ColorsRepository colorsRepository) {

		People people = new People(peopleReq, genderRepository, colorsRepository);
		

		repository.save(people);
		PeopleDtoResponseModel response = people.toResponseDto();

		return response;
	}

	@Override
	public PeopleDtoResponseModel updatePeople(Integer peopleId, UpdatePeopleRequestModel userReq,
			PeopleRepository peopleRepository, GenderRepository genderRepository,
			ColorsRepository colorsRepository) {

		People peopleUpdated = peopleRepository.findById(peopleId).get();

		peopleUpdated.setEdited(Instant.now());

		String nameReq = userReq.getName();
		String birth_yearReq = userReq.getBirth_year();
		Integer heightReq = userReq.getHeight();
		Integer massReq = userReq.getMass();
		String genderReq = userReq.getGender();
		List<String> eye_colorReq = userReq.getEye_color();
//		List<String> skin_colorReq = userReq.getSkin_color();
//		List<String> hair_colorReq = userReq.getHair_color();

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

			System.out.println(gender);

			peopleUpdated.setGender(gender);
		}
		
		PeopleDtoResponseModel responseDto = peopleUpdated.toResponseDto();

		if (eye_colorReq != null && !eye_colorReq.isEmpty()) {
			
			EyeColor corOlho = new EyeColor();
			
			for (int i = 0; i < eye_colorReq.size(); i++) {
				
				//System.out.println(eye_colorReq.get(i));
				
				Colors color = colorsRepository.findByName(eye_colorReq.get(i));
				corOlho.setColor(color);
				
				corOlho.setPeople(peopleUpdated);
				responseDto.setEye_color(corOlho);
				
			}
			
		}
		
		//System.out.println("responseDto: " + responseDto.getEye_colors());
		
		return responseDto;

	}

	@Override
	public void deletePeople(Integer peopleId, PeopleRepository repository) {

		repository.deleteById(peopleId);

	}

}
