package com.vipdsilva.app.ws.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import com.vipdsilva.app.ws.entities.Colors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class ColorsRepositoryTest {

	@Autowired
	ColorsRepository colorsRepository;

	// GET
	@Test
	public void shouldLoadAColorByName() {
		String colorName = "branco";
		Colors whiteColor = colorsRepository.findByName(colorName);
		assertNotNull(whiteColor);
		assertEquals(colorName, whiteColor.getName());
	}

	@Test
	public void shouldNotLoadAColorThatDoesNotExist() {
		String colorName = "azul banana fosco";
		Colors notFoundColor = colorsRepository.findByName(colorName);
		assertNull(notFoundColor);
	}

	// CREATE

	@Test
	public void shouldCreateNewColor() {
		String colorName = "cinza";

		Colors cinzaColor = new Colors(colorName);
		colorsRepository.save(cinzaColor);

		Colors newColor = colorsRepository.findByName(colorName);
		
		assertNotNull(newColor);
		assertEquals(colorName, newColor.getName());
	}


	// DELETE

	@Test
	public void shouldDeleteColor() {
		String colorName = "preto";

		Colors color = colorsRepository.findByName(colorName);
		colorsRepository.delete(color);

		Colors deletedColor = colorsRepository.findByName(colorName);
		
		assertNull(deletedColor);
	}

	// UPDATE

	@Test
	public void shouldUpdateColor() {
		String colorName = "bege claro";
		String colorNameUpdated = "bege";

		Colors color = colorsRepository.findByName(colorName);
		color.setName(colorNameUpdated);

		Colors updatedColor = colorsRepository.findByName(colorNameUpdated);
		
		assertNotNull(updatedColor);
		assertEquals(colorNameUpdated, updatedColor.getName());
	}


	
}
