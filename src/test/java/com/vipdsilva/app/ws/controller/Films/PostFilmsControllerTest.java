package com.vipdsilva.app.ws.controller.Films;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.web.util.NestedServletException;

public class PostFilmsControllerTest extends ApplicationConfigTest {

	@Autowired
    private MockMvc mockMvc;

	@Autowired
	private FilmsRepository filmsRepository;

	private AuthService authService;
	private DataService dataService;

	private String URL_FILMS;
	private String titleFilm;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.authService = new AuthService(mockMvc);
		this.dataService = new DataService();
		this.URL_FILMS = this.dataService.getDataFilm().getString("url");
		this.titleFilm = this.dataService.getDataFilm().getString("titleEFilmGET");
   }

	/**
	* Method: POST
	*/

	@Test
    public void shouldNotPostAFilmWithoutAuth() throws Exception {
        URI uri = new URI(URL_FILMS);

		JSONObject new_film = new JSONObject();
		new_film.put("title", "Han Solo");

       mockMvc
        .perform(MockMvcRequestBuilders
				.post(uri)
				.content(new_film.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(403));
    }

	@Test
    public void shouldPostAFilm() throws Exception {
        
		String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");

		URI uri = new URI(URL_FILMS);

		JSONObject new_film = this.dataService.getNewFilm();

		Integer totalFilmsBefore = (int) filmsRepository.count();

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", tokenMod)
				.content(new_film.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(201))
				.andReturn();
		
		JSONObject json = this.dataService.resultToJson(result);  
		Integer totalFilmsAfter = (int) filmsRepository.count();
		String newTitle = json.getString("title");
		
		assertEquals(totalFilmsBefore+1, totalFilmsAfter);
		assertEquals(new_film.getString("title"), newTitle);

    }

	@Test
    public void shouldNotPostAnExistingFilm() throws Exception {
		
        String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		
		URI uri = new URI(URL_FILMS);

		JSONObject new_film = new JSONObject();
		new_film.put("title", titleFilm);

        
		Exception exception = assertThrows(NestedServletException.class, 
		() -> { 
			mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.header("Authorization", tokenMod)
					.content(new_film.toString())
					.contentType(MediaType.APPLICATION_JSON))
					.andReturn();
			}
		);
		
		String messageError = "J?? possui um filme com esse title";
		
		assertTrue(exception.getMessage().contains(messageError));

    }

}
