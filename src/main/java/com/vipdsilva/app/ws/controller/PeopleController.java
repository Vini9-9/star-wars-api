package com.vipdsilva.app.ws.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.repository.EyeColorsRepository;
import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.repository.GenderRepository;
import com.vipdsilva.app.ws.repository.HairColorsRepository;
import com.vipdsilva.app.ws.repository.PeopleRepository;
import com.vipdsilva.app.ws.repository.SkinColorsRepository;
import com.vipdsilva.app.ws.service.PeopleService;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.exceptions.NotFoundException;
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.request.UpdatePeopleRequestModel;
import com.vipdsilva.app.ws.model.response.DeleteDtoResponseModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;

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

	@GetMapping
	public ResponseEntity<Page<PeopleDtoResponseModel>> listPessoas(
		@RequestParam(required = false, defaultValue = "0") int pagina,
		@RequestParam(required = false, defaultValue = "5") int qtd) {

		Pageable paginacao = PageRequest.of(pagina, qtd);

		Page<People> pessoas = peopleRepository.findAll(paginacao);
		
		if (pessoas.isEmpty()) {
			
			throw new NotFoundException("Nenhuma pessoa cadastrada");
			
		} else {

			Page<PeopleDtoResponseModel> listaPessoasDto = PeopleDtoResponseModel.converter(pessoas);
			
			return new ResponseEntity<Page<PeopleDtoResponseModel>>(listaPessoasDto, HttpStatus.OK);
		}

	}

	@GetMapping("/{peopleId}")
	public ResponseEntity<PeopleDtoResponseModel> getPessoa(@PathVariable Integer peopleId) {

		Optional<People> person = peopleRepository.findById(peopleId);

		if (person.isPresent()) {

			PeopleDtoResponseModel response = person.get().toResponseDto();

			return new ResponseEntity<PeopleDtoResponseModel>(response, HttpStatus.OK);

		} else {
			
			throw new NotFoundException("Pessoa com id " + peopleId + " não localizada");
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
	public ResponseEntity<DeleteDtoResponseModel> deleta(@PathVariable Integer peopleId) {


		Optional<People> person = peopleRepository.findById(peopleId);

		if (person.isPresent()) {

			peopleService.deletePeople(peopleId, peopleRepository,
					eyeColorsRepository, hairColorsRepository, skinColorsRepository);
			
			DeleteDtoResponseModel responseMsg = new DeleteDtoResponseModel("peopleID: " + peopleId + " deletado com sucesso");

			return new ResponseEntity<DeleteDtoResponseModel>(responseMsg, HttpStatus.OK);

		} else {

			throw new NotFoundException("peopleID: " + peopleId + " não localizado");

		}

	}

}
