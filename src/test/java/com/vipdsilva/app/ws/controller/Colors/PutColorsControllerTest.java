package com.vipdsilva.app.ws.controller.Colors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import com.vipdsilva.app.ws.ApplicationConfigTest;
import com.vipdsilva.app.ws.controller.service.AuthService;
import com.vipdsilva.app.ws.controller.service.DataService;
import com.vipdsilva.app.ws.repository.ColorsRepository;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class PutColorsControllerTest extends ApplicationConfigTest {

	@Autowired
    private MockMvc mockMvc;

	@Autowired
	private ColorsRepository colorsRepository;

	private AuthService authService;
	private DataService dataService;

    private String URL_COLORS;
	private String newColorName;
	private Integer idEColor;
	private Integer idNEColor;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.authService = new AuthService(mockMvc);
		this.dataService = new DataService();
		this.URL_COLORS = this.dataService.getDataColor().getString("url");
		this.idEColor = this.dataService.getDataColor().getInt("idEColor");
		this.idNEColor = this.dataService.getDataColor().getInt("idNEColor");
		this.newColorName = this.dataService.getDataColor().getString("newColorNamePUT");
   }

	/**
	* Method: PUT
	*/

	@Test
    public void shouldNotPutAColorWithoutAuth() throws Exception {
		URI uri = new URI(URL_COLORS + idEColor);

		JSONObject updatedColor = new JSONObject();
		updatedColor.put("name", "laranja");

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
    public void shouldPutAColor() throws Exception {
        
		String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		URI uri = new URI(URL_COLORS + idEColor);

		Integer totalColorsBefore = (int) colorsRepository.count();

		JSONObject updatedColor = new JSONObject();
		updatedColor.put("name", newColorName);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.put(uri)
				.header("Authorization", tokenMod)
				.content(updatedColor.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		
		JSONObject json = this.dataService.resultToJson(result);  
		Integer totalColorsAfter = (int) colorsRepository.count(); // valor de acordo com o BD
		String nameUpdated = json.getString("name");
		Integer idUpdated = json.getInt("id");
				
		assertEquals(totalColorsBefore, totalColorsAfter);
		assertEquals(idEColor, idUpdated);
		assertEquals(updatedColor.getString("name"), nameUpdated);

    }

	@Test
    public void shouldNotPutAnInexistingColor() throws Exception {
		
        String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		
		URI uri = new URI(URL_COLORS + idNEColor);

		JSONObject new_color = new JSONObject();
		new_color.put("name", "verde lima");

        
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders
					.put(uri)
					.header("Authorization", tokenMod)
					.content(new_color.toString())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers
					.status()
					.is(404))
					.andReturn();

		String jsonMessage =  this.dataService.resultToJson(result).getString("message");
		String messageError = "Cor com id " + idNEColor + " não localizada";

		assertEquals(messageError, jsonMessage);
    }

}
