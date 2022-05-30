package com.vipdsilva.app.ws.controller.People;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.web.util.NestedServletException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostPeopleControllerTest {

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

	@Test
    public void shouldNotPostAnExistingPerson() throws Exception {
		
        String tokenMod = "Bearer " + 
		this.authService.authAsModerador().getString("token");
		
		URI uri = new URI(URL_PEOPLE);

		JSONObject new_person = this.dataService.getNewEPeople();
        
		Exception exception = assertThrows(NestedServletException.class, 
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
		
		assertTrue(exception.getMessage().contains(messageError));

    }

}
