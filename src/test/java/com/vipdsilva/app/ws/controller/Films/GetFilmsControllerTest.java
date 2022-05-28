package com.vipdsilva.app.ws.controller.Films;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import com.vipdsilva.app.ws.repository.FilmsRepository;
import com.vipdsilva.app.ws.service.DataService;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetFilmsControllerTest {

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
		this.idEFilm = this.dataService.getDataFilm().getInt("idEFilm");
		this.titleFilm = this.dataService.getDataFilm().getString("titleEFilm");
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
		String messageError = "Filme com id " + this.idNEFilm + " não localizado";

		assertEquals(messageError, jsonMessage);

    }

}
