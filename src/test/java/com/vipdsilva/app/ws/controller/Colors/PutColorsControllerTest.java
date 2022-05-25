package com.vipdsilva.app.ws.controller.Colors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.service.AuthService;
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
public class PutColorsControllerTest {

	@Autowired
    private MockMvc mockMvc;

	@Autowired
	private ColorsRepository colorsRepository;

	private AuthService authService;
	private DataService dataService;

	@BeforeEach
	private void initEach() {
		this.authService = new AuthService(mockMvc);
		this.dataService = new DataService();
   }

	/**
	* Method: POST
	*/

	@Test
    public void shouldNotPutAColorWithoutAuth() throws Exception {
        Integer idColor = 1;
		URI uri = new URI("/api/colors/" + idColor);

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
		Integer idColorBege = 8;
		URI uri = new URI("/api/colors/" + idColorBege);

		Integer totalColorsBefore = (int) colorsRepository.count();

		JSONObject updatedColor = new JSONObject();
		updatedColor.put("name", "bege");

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
		assertEquals(idColorBege, idUpdated);
		assertEquals(updatedColor.getString("name"), nameUpdated);

    }

	@Test
    public void shouldNotPutAnInexistingColor() throws Exception {
		
        String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		
		Integer idInexistingColor = 99;
		URI uri = new URI("/api/colors/" + idInexistingColor);

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

		String messageError = "colorID: " + idInexistingColor + " não localizado";

		byte[] bytes = StringUtils.getBytes(jsonMessage, StandardCharsets.ISO_8859_1);
		String utf8String = StringUtils.toEncodedString(bytes, StandardCharsets.UTF_8);

		assertEquals(messageError, utf8String);
    }

}
