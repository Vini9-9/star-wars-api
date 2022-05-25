package com.vipdsilva.app.ws.controller.Colors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import com.vipdsilva.app.ws.service.AuthService;
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
import org.springframework.web.util.NestedServletException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostColorsControllerTest {

	@Autowired
    private MockMvc mockMvc;

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
    public void shouldNotPostAColorWithoutAuth() throws Exception {
        URI uri = new URI("/api/colors/");

		JSONObject new_color = new JSONObject();
		new_color.put("name", "laranja");

       mockMvc
        .perform(MockMvcRequestBuilders
				.post(uri)
				.content(new_color.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(403));
    }

	@Test
    public void shouldPostAColor() throws Exception {
        
		String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		
		URI uri = new URI("/api/colors/");

		JSONObject new_color = new JSONObject();
		new_color.put("name", "laranja");

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", tokenMod)
				.content(new_color.toString())
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(201))
				.andReturn();
		
		JSONObject json = this.dataService.resultToJson(result);  
		Integer totalColors = 12; // valor de acordo com o BD
		Integer newId = json.getInt("id");
		String newName = json.getString("name");
				
		assertEquals(totalColors, newId);
		assertEquals(new_color.getString("name"), newName);

    }

	@Test
    public void shouldNotPostAnExistingColor() throws Exception {
		
        String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		
		URI uri = new URI("/api/colors/");

		JSONObject new_color = new JSONObject();
		new_color.put("name", "azul");

        
		Exception exception = assertThrows(NestedServletException.class, 
		() -> { 
			mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.header("Authorization", tokenMod)
					.content(new_color.toString())
					.contentType(MediaType.APPLICATION_JSON))
					.andReturn();
			}
		);
		
		String messageError = "Cor já cadastrada";
		
		assertTrue(exception.getMessage().contains(messageError));

    }

}
