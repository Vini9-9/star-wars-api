package com.vipdsilva.app.ws.controller.Colors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import com.vipdsilva.app.ws.repository.ColorsRepository;
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
public class GetColorsControllerTest {

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
    public void shouldGetAColor() throws Exception {

        URI uri = new URI(URL_COLORS + idEColor);

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
				
		assertEquals(idEColor, jsonId);
		assertEquals(nameColor, jsonName);

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
