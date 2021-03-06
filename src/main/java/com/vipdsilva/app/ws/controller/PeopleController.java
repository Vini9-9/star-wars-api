package com.vipdsilva.app.ws.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.exceptions.NotFoundPeopleException;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.model.response.WarningDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.repository.EyeColorsRepository;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.repository.GenderRepository;
import com.vipdsilva.app.ws.repository.HairColorsRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.repository.SkinColorsRepository;
import com.vipdsilva.app.ws.service.PeopleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

	// Injeção do Repository
	@Autowired
	private PeopleRepository peopleRepository;
	
	@Autowired
	private FilmsRepository filmsRepository;
	
	@Autowired
	private GenderRepository genderRepository;
	
	@Autowired
	private ColorsRepository colorsRepository;
	
	@Autowired
	private EyeColorsRepository eyeColorsRepository;
	
	@Autowired
	private HairColorsRepository hairColorsRepository;
	
	@Autowired
	private SkinColorsRepository skinColorsRepository;

	@Autowired
	PeopleService peopleService;

	@GetMapping("/all")
	public ResponseEntity<Page<PeopleDtoResponseModel>> listPessoas(
		@PageableDefault(sort = "name", page = 0, size = 5) Pageable paginacao
		) {

		Page<People> pessoas = peopleRepository.findAll(paginacao);
		
		if (pessoas.isEmpty()) {
			
			throw new NotFoundPeopleException();
			
		} else {

			Page<PeopleDtoResponseModel> listaPessoasDto = PeopleDtoResponseModel.converter(pessoas);
			
			return new ResponseEntity<Page<PeopleDtoResponseModel>>(listaPessoasDto, HttpStatus.OK);
		}

	}

	@GetMapping("/{peopleId}")
	public ResponseEntity<PeopleDtoResponseModel> getPessoa(
		@PathVariable Integer peopleId
		) {

		Optional<People> person = peopleRepository.findById(peopleId);

		if (person.isPresent()) {

			PeopleDtoResponseModel response = person.get().toResponseDto();

			return new ResponseEntity<PeopleDtoResponseModel>(response, HttpStatus.OK);

		} else {
			
			throw new NotFoundPeopleException(peopleId);
		}

	}

	@PostMapping
	public ResponseEntity<PeopleDtoResponseModel> adicionar(@ModelAttribute People people,
	@RequestBody PeopleDtoRequestModel peopleDetails) {

		
		PeopleDtoResponseModel returnValue = peopleService.createPeople(peopleDetails,
				peopleRepository, genderRepository, colorsRepository, filmsRepository);

		return new ResponseEntity<PeopleDtoResponseModel>(returnValue, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{peopleId}")
	@Transactional
	public ResponseEntity<PeopleDtoResponseModel> atualiza(@PathVariable Integer peopleId,
			@RequestBody UpdatePeopleRequestModel body) {
				

		PeopleDtoResponseModel returnValue = peopleService.updatePeople(peopleId, body,
				peopleRepository, genderRepository, colorsRepository, filmsRepository);

		return new ResponseEntity<PeopleDtoResponseModel>(returnValue, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{peopleId}")
	@Transactional
	public ResponseEntity<WarningDtoResponseModel> deleta(@PathVariable Integer peopleId) {


		Optional<People> person = peopleRepository.findById(peopleId);

		if (person.isPresent()) {

			peopleService.deletePeople(peopleId, peopleRepository,
					eyeColorsRepository, hairColorsRepository, skinColorsRepository);
			
			WarningDtoResponseModel responseMsg = new WarningDtoResponseModel(
				"Pessoa", peopleId);

			return new ResponseEntity<WarningDtoResponseModel>(responseMsg, HttpStatus.OK);

		} else {

			throw new NotFoundPeopleException(peopleId);

		}

	}

}
