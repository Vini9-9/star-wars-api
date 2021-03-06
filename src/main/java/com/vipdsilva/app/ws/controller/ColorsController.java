package com.vipdsilva.app.ws.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import com.vipdsilva.app.ws.entities.Colors;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.exceptions.NotFoundColorException;
import com.vipdsilva.app.ws.model.request.ColorRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateColorRequestModel;
import com.vipdsilva.app.ws.model.response.ColorsDtoResponseModel;
import com.vipdsilva.app.ws.model.response.WarningDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.service.ColorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@RequestMapping("/api/colors")
public class ColorsController {

	// Injeção do Repository
	@Autowired
	private ColorsRepository colorsRepository;

	@Autowired
	ColorService colorService;

	@GetMapping
	@Cacheable(value = "ColorsList")
	public ResponseEntity<Page<ColorsDtoResponseModel>> listColors(
		@PageableDefault(sort = "name", page = 0, size = 15) Pageable paginacao
		) {

		Page<Colors> colors = colorsRepository.findAll(paginacao);
		
		if (colors.isEmpty()) {
			
			throw new NotFoundColorException();
			
		} else {

			Page<ColorsDtoResponseModel> listColorsDto = ColorsDtoResponseModel.convert(colors);

			return new ResponseEntity<Page<ColorsDtoResponseModel>>(listColorsDto, HttpStatus.OK);
		}

	}

	@GetMapping("/{colorId}")
	public ResponseEntity<ColorsDtoResponseModel> getColor(
		@PathVariable Integer colorId
		) {

		Optional<Colors> color = colorsRepository.findById(colorId);

		if (color.isPresent()) {

			ColorsDtoResponseModel response = color.get().toResponseDto();

			return new ResponseEntity<ColorsDtoResponseModel>(response, HttpStatus.OK);

		} else {
			
			throw new NotFoundColorException(colorId);
		}

	}

	@PostMapping
	@CacheEvict(value = "ColorsList", allEntries = true)
	public ResponseEntity<ColorsDtoResponseModel> addColor(@ModelAttribute People people,
	@RequestBody ColorRequestModel colorInfo) {

		ColorsDtoResponseModel returnValue = colorService.createColor(colorInfo, colorsRepository);

		return new ResponseEntity<ColorsDtoResponseModel>(returnValue, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{colorId}")
	@Transactional
	@CacheEvict(value = "ColorsList", allEntries = true)
	public ResponseEntity<ColorsDtoResponseModel> update(@PathVariable Integer colorId,
			@RequestBody UpdateColorRequestModel body) {
				

		ColorsDtoResponseModel returnValue = colorService.updateColor(colorId, body,
				colorsRepository);

		return new ResponseEntity<ColorsDtoResponseModel>(returnValue, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{colorId}")
	@Transactional
	@CacheEvict(value = "ColorsList", allEntries = true)
	public ResponseEntity<WarningDtoResponseModel> delete(@PathVariable Integer colorId) {

		colorService.deleteColor(colorId, colorsRepository);

		WarningDtoResponseModel responseMsg = 
		new WarningDtoResponseModel("Cor", colorId);

		return new ResponseEntity<WarningDtoResponseModel>(responseMsg, HttpStatus.OK);

	}

}
