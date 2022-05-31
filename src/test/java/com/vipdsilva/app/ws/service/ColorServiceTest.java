package com.vipdsilva.app.ws.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.vipdsilva.app.ws.ApplicationConfigTest;
import com.vipdsilva.app.ws.entities.Colors;
import com.vipdsilva.app.ws.exceptions.NotFoundColorException;
import com.vipdsilva.app.ws.model.request.ColorRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateColorRequestModel;
import com.vipdsilva.app.ws.model.response.ColorsDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.service.impl.ColorServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

public class ColorServiceTest extends ApplicationConfigTest {
	
	@Mock
	private ColorsRepository colorsRepository;
    
	@Mock
	private ColorRequestModel colorInfo;

	@Captor
	private ArgumentCaptor<Colors> captorColor;

    @Autowired
    private ColorServiceImpl colorService;
	
    @BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
        this.colorService = new ColorServiceImpl();
	}

	@Test
	void ShouldCreateANewColor() {
        ColorRequestModel colorInfo = generateColorInfo();
        String nameReqColor = colorInfo.getName();

        Mockito
		.when(colorsRepository.findByName(ArgumentMatchers.eq(nameReqColor)))
        .thenReturn(null);

        ColorsDtoResponseModel response = colorService.createColor(colorInfo, colorsRepository);

		Mockito.verify(colorsRepository).save(captorColor.capture());

		Colors color = captorColor.getValue();

		assertEquals(nameReqColor, color.getName());
		assertEquals(response.getName(), color.toResponseDto().getName());
		
	}

	@Test
	void ShouldNotCreateANExistingColor() {
        ColorRequestModel colorInfo = generateColorInfo();
        String nameReqColor = colorInfo.getName();

        Mockito
		.when(colorsRepository.findByName(ArgumentMatchers.eq(nameReqColor)))
        .thenReturn(new Colors("cor"));

		try {
			colorService.createColor(colorInfo, colorsRepository);
			Mockito.verifyNoInteractions(colorsRepository);
		} catch (Exception e) {
			assertEquals("Já possui uma cor com esse nome", e.getMessage());
		}
		
	}

	@Test
	void ShouldUpdateAColor() {
        Integer colorId = 1;
		String nameColorToUpdate = "azul banana fosco";
		UpdateColorRequestModel userDetails = new UpdateColorRequestModel();
		userDetails.setName(nameColorToUpdate);
        
		Optional<Colors> optColor = Optional.of(new Colors("azul"));

		Mockito
		.when(colorsRepository.findById(ArgumentMatchers.eq(colorId)))
        .thenReturn(optColor);

		Mockito
		.when(colorsRepository.findByName(ArgumentMatchers.eq(nameColorToUpdate)))
        .thenReturn(null);

		ColorsDtoResponseModel response = colorService.updateColor(colorId, userDetails,
		 colorsRepository);

		assertEquals(nameColorToUpdate, response.getName());
		
	}

	@Test
	void ShouldNotUpdateAColorWithAnExistingName() {
        Integer colorId = 1;
		String nameColorToUpdate = "azul";
		UpdateColorRequestModel userDetails = new UpdateColorRequestModel();
		userDetails.setName(nameColorToUpdate);

		Colors existColor = new Colors(nameColorToUpdate);
        
		Optional<Colors> optColor = Optional.of(new Colors("branco"));

		Mockito
		.when(colorsRepository.findById(ArgumentMatchers.eq(colorId)))
        .thenReturn(optColor);

		Mockito
		.when(colorsRepository.findByName(ArgumentMatchers.eq(nameColorToUpdate)))
        .thenReturn(existColor);


		 try {
			colorService.updateColor(colorId, userDetails, colorsRepository);
			Mockito.verifyNoInteractions(optColor);
			Mockito.verifyNoInteractions(colorsRepository);
		} catch (Exception e) {
			assertEquals("Já possui uma cor com esse nome", e.getMessage());
		}
		
	}

	@Test
	void ShouldNotFindAColorByInexistingId() {
        Integer colorId = 99;
		String nameColorToUpdate = "azul";
		UpdateColorRequestModel userDetails = new UpdateColorRequestModel();
		userDetails.setName(nameColorToUpdate);

		Optional<Colors> emptyColor = Optional.empty();

		Mockito
		.when(colorsRepository.findById(ArgumentMatchers.eq(colorId)))
        .thenReturn(emptyColor);

		 try {
			colorService.updateColor(colorId, userDetails, colorsRepository);
			Mockito.verifyNoInteractions(emptyColor);
			Mockito.verifyNoInteractions(colorsRepository);
		} catch (Exception e) {
			assertEquals("Cor com id " + colorId + " não localizada", e.getMessage());
		}
		
	}

	@Test
	void ShouldDeleteAColor() {
        Integer colorId = 1;
		colorService.deleteColor(colorId, colorsRepository);
		Mockito.verify(colorsRepository).deleteById(colorId);
		
	}

	@Test
	void ShouldNotDeleteAInexistingColor() {
        Integer colorId = 99;

		Mockito
		.doThrow(new NotFoundColorException(colorId))
		.when(colorsRepository).deleteById(colorId);

		 try {
			colorService.deleteColor(colorId, colorsRepository);
			Mockito.verifyNoInteractions(colorsRepository);
		} catch (Exception e) {
			assertEquals("Cor com id " + colorId + " não localizada", e.getMessage());
		}
		
	}

	private ColorRequestModel generateColorInfo() {
		ColorRequestModel colorInfo = new ColorRequestModel("cor");
		return colorInfo;
	}
    
}
