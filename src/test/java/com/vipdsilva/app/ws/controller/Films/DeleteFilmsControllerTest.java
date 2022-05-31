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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DeleteFilmsControllerTest extends ApplicationConfigTest {

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
		this.idEFilm = this.dataService.getDataFilm().getInt("idEFilmDELETE");
		this.idNEFilm = this.dataService.getDataFilm().getInt("idNEFilm");
   }

	@Test
    public void shouldNotDeleteAFilmWithoutAuth() throws Exception {

        URI uri = new URI(URL_FILMS + idEFilm);

       mockMvc
        .perform(MockMvcRequestBuilders
				.delete(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(403));
    }

	@Test
    public void shouldDeleteAFilm() throws Exception {

		Integer totalFilmsBefore = (int) filmsRepository.count();
        
		String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");

        URI uri = new URI(URL_FILMS + idEFilm);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.delete(uri)
				.header("Authorization", tokenMod))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		

		JSONObject json = this.dataService.resultToJson(result); 

		Integer totalFilmsAfter = (int) filmsRepository.count(); 
		String messageExpected = "Filme com id " + idEFilm + " deletado com sucesso";
		String messageDelete = json.getJSONObject("warning").getString("message");
				
		assertEquals(totalFilmsBefore-1, totalFilmsAfter);
		assertEquals(messageExpected, messageDelete);

    }

	@Test
    public void shouldNotDeleteAnInexistingFilm() throws Exception {
		
        String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		
		URI uri = new URI(URL_FILMS + idNEFilm);

		MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders
				.delete(uri)
				.header("Authorization", tokenMod))
				.andReturn();

		String messageError = "Filme com id " + idNEFilm + " não localizado";

		JSONObject json = this.dataService.resultToJson(result);

		assertEquals(json.getString("message"), messageError);
		
    }

}
