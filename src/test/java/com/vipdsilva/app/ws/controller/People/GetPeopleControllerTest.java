package com.vipdsilva.app.ws.controller.People;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import com.vipdsilva.app.ws.repository.PeopleRepository;
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
public class GetPeopleControllerTest {

	@Autowired
    private MockMvc mockMvc;

	private DataService dataService;

	@Autowired
	private PeopleRepository peopleRepository;

	private String URL_PEOPLE;
	private String nameEPeople;
	private int idEPeople;
	private int idNEPeople;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.dataService = new DataService();
		this.URL_PEOPLE = this.dataService.getDataPeople().getString("url");
		this.idEPeople = this.dataService.getDataPeople().getInt("idEPeople");
		this.idNEPeople = this.dataService.getDataPeople().getInt("idNEPeople");
		this.nameEPeople = this.dataService.getDataPeople().getString("nameEPeople");
   }

	/**
	* Method: GET
	*/ 
	@Test
    public void shouldGetAllPeople() throws Exception {
        URI uri = new URI(URL_PEOPLE + "all");

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		
		JSONObject json = this.dataService.resultToJson(result);  
		Integer totalPeople = (int) peopleRepository.count();
		Integer totalElements = json.getInt("totalElements");
				
		assertEquals(totalPeople, totalElements);

    }


	@Test
    public void shouldGetAPerson() throws Exception {

        URI uri = new URI(URL_PEOPLE + idEPeople);

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
		String jsonName = json.getString("name");
				
		assertEquals(idEPeople, jsonId);
		assertEquals(nameEPeople, jsonName);

    }

	@Test
    public void shouldNotFindAPerson() throws Exception {

        URI uri = new URI(URL_PEOPLE + idNEPeople);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(404))
				.andReturn();
		

		JSONObject json = this.dataService.resultToJson(result);

		String jsonMessage = json.getString("message");
		String messageError = "Pessoa com id " + idNEPeople + " não localizada";

		assertEquals(messageError, jsonMessage);

    }

}
