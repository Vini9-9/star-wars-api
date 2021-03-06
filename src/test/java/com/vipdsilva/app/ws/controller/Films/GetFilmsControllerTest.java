package com.vipdsilva.app.ws.controller.Films;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import com.vipdsilva.app.ws.ApplicationConfigTest;
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

public class GetFilmsControllerTest extends ApplicationConfigTest {

	@Autowired
    private MockMvc mockMvc;

	private DataService dataService;

	@Autowired
	private FilmsRepository filmsRepository;

	private String URL_FILMS;
	private int idEFilm;
	private String titleFilm;
	private int idNEFilm;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.dataService = new DataService();
		this.URL_FILMS = this.dataService.getDataFilm().getString("url");
		this.idEFilm = this.dataService.getDataFilm().getInt("idEFilmGET");
		this.titleFilm = this.dataService.getDataFilm().getString("titleEFilmGET");
		this.idNEFilm = this.dataService.getDataFilm().getInt("idNEFilm");
   }

	/**
	* Method: GET
	*/ 
	@Test
    public void shouldGetAllFilms() throws Exception {
        URI uri = new URI(URL_FILMS + "all");

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		
		JSONObject json = this.dataService.resultToJson(result);  
		Integer totalFilms = (int) filmsRepository.count();
		Integer totalElements = json.getInt("totalElements");
				
		assertEquals(totalFilms, totalElements);

    }


	@Test
    public void shouldGetAFilm() throws Exception {

        URI uri = new URI(URL_FILMS + idEFilm);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		

		JSONObject json = this.dataService.resultToJson(result);

		Integer jsonId = json.getInt("id");
		String jsonTitle = json.getString("title");
				
		assertEquals(idEFilm, jsonId);
		assertEquals(titleFilm, jsonTitle);

    }

	@Test
    public void shouldNotFindAFilm() throws Exception {

        URI uri = new URI(URL_FILMS + idNEFilm);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(404))
				.andReturn();
		

		JSONObject json = this.dataService.resultToJson(result);

		String jsonMessage = json.getString("message");
		String messageError = "Filme com id " + this.idNEFilm + " n??o localizado";

		assertEquals(messageError, jsonMessage);

    }

}
