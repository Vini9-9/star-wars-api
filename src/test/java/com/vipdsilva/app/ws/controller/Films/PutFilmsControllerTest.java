package com.vipdsilva.app.ws.controller.Films;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import com.vipdsilva.app.ws.ApplicationConfigTest;
import com.vipdsilva.app.ws.controller.service.AuthService;
import com.vipdsilva.app.ws.controller.service.DataService;
import com.vipdsilva.app.ws.repository.FilmsRepository;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class PutFilmsControllerTest extends ApplicationConfigTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private FilmsRepository filmsRepository;
	
	private AuthService authService;
	private DataService dataService;

	private String URL_FILMS;
	private int idEFilm;
	private int idNEFilm;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.authService = new AuthService(mockMvc);
		this.dataService = new DataService();
		this.URL_FILMS = this.dataService.getDataFilm().getString("url");
		this.idEFilm = this.dataService.getDataFilm().getInt("idEFilm");
		this.idNEFilm = this.dataService.getDataFilm().getInt("idNEFilm");
   }

	/**
	* Method: PUT
	*/

	@Test
    public void shouldNotPutAFilmWithoutAuth() throws Exception {
		
		URI uri = new URI(URL_FILMS + idEFilm);
		JSONObject updatedColor = new JSONObject();
		updatedColor.put("title", "Filme atualizado");

       mockMvc
        .perform(MockMvcRequestBuilders
				.put(uri)
				.content(updatedColor.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(403));
    }

	@Test
    public void shouldPutAFilm() throws Exception {
        
		String tokenMod = "Bearer " + authService.authAsModerador().getString("token");

		URI uri = new URI(URL_FILMS + idEFilm);

		Integer totalFilmsBefore = (int) filmsRepository.count();

		JSONObject updatedFilm = new JSONObject();
		updatedFilm.put("title", "A Nova Esperança");

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.put(uri)
				.header("Authorization", tokenMod)
				.content(updatedFilm.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		
		JSONObject json = dataService.resultToJson(result);  
		Integer totalFilmsAfter = (int) filmsRepository.count(); // valor de acordo com o BD
		String titleUpdated = json.getString("title");
		Integer idUpdated = json.getInt("id");
				
		assertEquals(totalFilmsBefore, totalFilmsAfter);
		assertEquals(idEFilm, idUpdated);
		assertEquals(updatedFilm.getString("title"), titleUpdated);

    }

	@Test
    public void shouldNotPutAnInexistingFilm() throws Exception {
		
        String tokenMod = "Bearer " + authService.authAsModerador().getString("token");
		
		URI uri = new URI(URL_FILMS + idNEFilm);

		JSONObject new_film = new JSONObject();
		new_film.put("title", "Filme Novo");

		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders
					.put(uri)
					.header("Authorization", tokenMod)
					.content(new_film.toString())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers
					.status()
					.is(404))
					.andReturn();

		String jsonMessage =  dataService.resultToJson(result).getString("message");
		String messageError = "Filme com id " + this.idNEFilm + " não localizado";

		assertEquals(messageError, jsonMessage);
    }

}
