package com.vipdsilva.app.ws.controller.People;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import com.vipdsilva.app.ws.ApplicationConfigTest;
import com.vipdsilva.app.ws.controller.service.AuthService;
import com.vipdsilva.app.ws.controller.service.DataService;
import com.vipdsilva.app.ws.repository.PeopleRepository;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DeletePeopleControllerTest extends ApplicationConfigTest {

	@Autowired
    private MockMvc mockMvc;

	@Autowired
	private PeopleRepository peopleRepository;

	private AuthService authService;
	private DataService dataService;

	private String URL_PEOPLE;
	private int idEPeople;
	private int idNEPeople;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.authService = new AuthService(mockMvc);
		this.dataService = new DataService();
		this.URL_PEOPLE = this.dataService.getDataPeople().getString("url");
		this.idEPeople = this.dataService.getDataPeople().getInt("idEPeople");
		this.idNEPeople = this.dataService.getDataPeople().getInt("idNEPeople");
   }

	@Test
    public void shouldNotDeleteAPersonWithoutAuth() throws Exception {

        URI uri = new URI(URL_PEOPLE + idEPeople);

       mockMvc
        .perform(MockMvcRequestBuilders
				.delete(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(403));
    }

	@Test
    public void shouldDeleteAPerson() throws Exception {

		Integer totalPeopleBefore = (int) peopleRepository.count();
        
		String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");

        URI uri = new URI(URL_PEOPLE + idEPeople);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.delete(uri)
				.header("Authorization", tokenMod))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		

		JSONObject json = this.dataService.resultToJson(result); 

		Integer totalPeopleAfter = (int) peopleRepository.count(); 
		String messageExpected = "Pessoa com id " + idEPeople + " deletado com sucesso";
		String messageDelete = json.getJSONObject("warning").getString("message");
				
		assertEquals(totalPeopleBefore-1, totalPeopleAfter);
		assertEquals(messageExpected, messageDelete);

    }

	@Test
    public void shouldNotDeleteAnInexistingPerson() throws Exception {
		
        String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		
		URI uri = new URI(URL_PEOPLE + idNEPeople);

		MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders
				.delete(uri)
				.header("Authorization", tokenMod))
				.andReturn();

		String messageError = "Pessoa com id " + idNEPeople + " não localizada";

		JSONObject json = this.dataService.resultToJson(result);

		assertEquals(json.getString("message"), messageError);
		
    }

}
