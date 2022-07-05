package com.vipdsilva.app.ws.controller.Colors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import com.vipdsilva.app.ws.ApplicationConfigTest;
import com.vipdsilva.app.ws.controller.service.DataService;
import com.vipdsilva.app.ws.repository.ColorsRepository;

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

public class GetColorsControllerTest extends ApplicationConfigTest {

	@Autowired
    private MockMvc mockMvc;

	private DataService dataService;

	@Autowired
	private ColorsRepository colorsRepository;

	private String URL_COLORS;
	private String nameColor;
	private Integer idEColor;
	private Integer idNEColor;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.dataService = new DataService();
		this.URL_COLORS = this.dataService.getDataColor().getString("url");
		this.idEColor = this.dataService.getDataColor().getInt("idEColor");
		this.idNEColor = this.dataService.getDataColor().getInt("idNEColor");
		this.nameColor = this.dataService.getDataColor().getString("nameEColor");
   }
	/**
	* Method: GET
	*/ 
	@Test
    public void shouldGetAllColors() throws Exception {
        URI uri = new URI(URL_COLORS);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		
		JSONObject json = this.dataService.resultToJson(result);  
		Integer totalColors = (int) colorsRepository.count();
		Integer totalElements = json.getInt("totalElements");
				
		assertEquals(totalColors, totalElements);

    }


	@Test
	@Sql("/insertColor.sql")
	@Sql(value = "/clearColor.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetAColor() throws Exception {

        URI uri = new URI(URL_COLORS + 100);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri)
				.content("")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		

		JSONObject json = this.dataService.resultToJson(result);

		Integer jsonId = json.getInt("id");
		String jsonName = json.getString("name");
				
		assertEquals(100, jsonId);
		assertEquals("vinho", jsonName);

    }

	@Test
    public void shouldNotFindAColor() throws Exception {

        URI uri = new URI(URL_COLORS + idNEColor);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri)
				.content("")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(404))
				.andReturn();
		

		JSONObject json = this.dataService.resultToJson(result);

		String jsonMessage = json.getString("message");
		String messageError = "Cor com id " + idNEColor + " não localizada";

		assertEquals(messageError, jsonMessage);

    }

}
