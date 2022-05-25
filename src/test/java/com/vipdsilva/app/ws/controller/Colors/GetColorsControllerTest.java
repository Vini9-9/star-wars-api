package com.vipdsilva.app.ws.controller.Colors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import com.vipdsilva.app.ws.service.DataService;

import org.apache.commons.lang3.StringUtils;
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

	@BeforeEach
	private void initEach() {
		this.dataService = new DataService();
   }
	/**
	* Method: GET
	*/ 
	@Test
    public void shouldGetAllColors() throws Exception {
        URI uri = new URI("/api/colors/");

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		
		JSONObject json = this.dataService.resultToJson(result);  
		Integer totalColors = this.dataService.getTotalColors();
		Integer numberOfElements = json.getInt("numberOfElements");
				
		assertEquals(totalColors, numberOfElements);

    }


	@Test
    public void shouldGetAColor() throws Exception {
		Integer idColor = 6;
		String nameColor = "azul";

        URI uri = new URI("/api/colors/" + idColor);

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
				
		assertEquals(idColor, jsonId);
		assertEquals(nameColor, jsonName);

    }

	@Test
    public void shouldNotFindAColor() throws Exception {
		Integer idNEColor = 99;

        URI uri = new URI("/api/colors/" + idNEColor);

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

		byte[] bytes = StringUtils.getBytes(jsonMessage, StandardCharsets.ISO_8859_1);
		String utf8String = StringUtils.toEncodedString(bytes, StandardCharsets.UTF_8);

		assertEquals(messageError, utf8String);

    }

}
