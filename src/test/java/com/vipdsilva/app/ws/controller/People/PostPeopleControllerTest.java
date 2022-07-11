package com.vipdsilva.app.ws.controller.People;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.vipdsilva.app.ws.ApplicationConfigTest;
import com.vipdsilva.app.ws.controller.service.AuthService;
import com.vipdsilva.app.ws.controller.service.DataService;
import com.vipdsilva.app.ws.repository.PeopleRepository;

public class PostPeopleControllerTest extends ApplicationConfigTest {

	@Autowired
    private MockMvc mockMvc;

	@Autowired
	private PeopleRepository peopleRepository;

	private AuthService authService;
	private DataService dataService;

	private String URL_PEOPLE;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.authService = new AuthService(mockMvc);
		this.dataService = new DataService();
		this.URL_PEOPLE = this.dataService.getDataPeople().getString("url");
   }

	/**
	* Method: POST
	*/

	@Test
    public void shouldNotPostAPersonWithoutAuth() throws Exception {
        URI uri = new URI(URL_PEOPLE);

		JSONObject new_person = this.dataService.getNewPeople();

        mockMvc
        .perform(MockMvcRequestBuilders
				.post(uri)
				.content(new_person.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(403));
    }

	// @Sql(value = "/insertFilms.sql")
	@Test
    public void shouldPostAPerson() throws Exception {
        
		String tokenMod = "Bearer " + this.authService.authAsModerador()
		.getString("token");

		URI uri = new URI(URL_PEOPLE);

		JSONObject new_person = this.dataService.getNewPeople();

		Integer totalPeopleBefore = (int) peopleRepository.count();

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", tokenMod)
				.content(new_person.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(201))
				.andReturn();
		
		JSONObject json = this.dataService.resultToJson(result);  
		Integer totalPeopleAfter = (int) peopleRepository.count();
		String newName = json.getString("name");
		
		assertEquals(totalPeopleBefore+1, totalPeopleAfter);
		assertEquals(new_person.getString("name"), newName);

    }

	// @Sql(value = "/clearPerson.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	@Sql("/insertEPerson.sql")
	@Sql(value = "/clearEPerson.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotPostAnExistingPerson() throws Exception {
		
		System.out.println("****** shouldNotPostAnExistingPerson()");
		
        String tokenMod = "Bearer " + 
		this.authService.authAsModerador().getString("token");
		
		URI uri = new URI(URL_PEOPLE);

		JSONObject new_person = this.dataService.getNewEPeople();
        
		Exception exception = assertThrows(Exception.class, 
		() -> { 
			mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.header("Authorization", tokenMod)
					.content(new_person.toString())
					.contentType(MediaType.APPLICATION_JSON))
					.andReturn();
			}
		);
		

		String messageError = "Já possui uma pessoa com esse nome";
		System.out.println("****** " + exception.getMessage());
		assertTrue(exception.getMessage().contains(messageError));

    }

}
