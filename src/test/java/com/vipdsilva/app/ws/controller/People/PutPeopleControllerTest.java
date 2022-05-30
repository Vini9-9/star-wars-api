package com.vipdsilva.app.ws.controller.People;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import com.vipdsilva.app.ws.controller.service.AuthService;
import com.vipdsilva.app.ws.controller.service.DataService;
import com.vipdsilva.app.ws.repository.PeopleRepository;

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
public class PutPeopleControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private PeopleRepository peopleRepository;
	
	private AuthService authService;
	private DataService dataService;

	private String URL_PEOPLE;
	private String newNamePeople;
	private int idEPeople;
	private int idNEPeople;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.authService = new AuthService(mockMvc);
		this.dataService = new DataService();
		this.URL_PEOPLE = this.dataService.getDataPeople().getString("url");
		this.idEPeople = this.dataService.getDataPeople().getInt("idEPeople");
		this.idNEPeople = this.dataService.getDataPeople().getInt("idNEPeople");
		this.newNamePeople = this.dataService.getDataPeople().getString("newPeopleNamePUT");
   }

	/**
	* Method: PUT
	*/

	@Test
    public void shouldNotPutAPersonWithoutAuth() throws Exception {
		
		URI uri = new URI(URL_PEOPLE + idEPeople);
		JSONObject updatedColor = new JSONObject();
		updatedColor.put("name", newNamePeople);

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
    public void shouldPutAPerson() throws Exception {
        
		String tokenMod = "Bearer " + authService.authAsModerador().getString("token");

		URI uri = new URI(URL_PEOPLE + idEPeople);

		Integer totalPeopleBefore = (int) peopleRepository.count();

		JSONObject updatedPeople = new JSONObject();
		updatedPeople.put("name", newNamePeople);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.put(uri)
				.header("Authorization", tokenMod)
				.content(updatedPeople.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		
		JSONObject json = dataService.resultToJson(result);  
		Integer totalPeopleAfter = (int) peopleRepository.count(); // valor de acordo com o BD
		String nameUpdated = json.getString("name");
		Integer idUpdated = json.getInt("id");
				
		assertEquals(totalPeopleBefore, totalPeopleAfter);
		assertEquals(idEPeople, idUpdated);
		assertEquals(updatedPeople.getString("name"), nameUpdated);

    }

	@Test
    public void shouldNotPutAnInexistingPerson() throws Exception {
		
        String tokenMod = "Bearer " + authService.authAsModerador().getString("token");
		
		URI uri = new URI(URL_PEOPLE + idNEPeople);

		JSONObject new_person = new JSONObject();
		new_person.put("name", newNamePeople);

		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders
					.put(uri)
					.header("Authorization", tokenMod)
					.content(new_person.toString())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers
					.status()
					.is(404))
					.andReturn();


		String jsonMessage =  dataService.resultToJson(result).getString("message");
		String messageError = "Pessoa com id " + idNEPeople + " não localizada";

		assertEquals(messageError, jsonMessage);
    }

}
