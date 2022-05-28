package com.vipdsilva.app.ws.controller.Colors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import com.vipdsilva.app.ws.repository.ColorsRepository;
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

	@Autowired
	private ColorsRepository colorsRepository;

	private AuthService authService;
	private DataService dataService;

	private String URL_COLORS;
	private String newColorName;
	private String nameColor;
	
	@BeforeEach
	private void initEach() throws Exception {
		this.authService = new AuthService(mockMvc);
		this.dataService = new DataService();
		this.URL_COLORS = this.dataService.getDataColor().getString("url");
		this.newColorName = this.dataService.getDataColor().getString("newColorNamePOST");
		this.nameColor = this.dataService.getDataColor().getString("nameEColor");
   }

	/**
	* Method: POST
	*/

	@Test
    public void shouldNotPostAColorWithoutAuth() throws Exception {
        URI uri = new URI(URL_COLORS);

		JSONObject new_color = new JSONObject();
		new_color.put("name", newColorName);

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
		
		URI uri = new URI(URL_COLORS);

		JSONObject new_color = new JSONObject();
		new_color.put("name", newColorName);

		Integer totalColorsBefore = (int) colorsRepository.count();

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
		Integer totalColorsAfter = (int) colorsRepository.count();
		String newName = json.getString("name");
				
		assertEquals(totalColorsBefore+1, totalColorsAfter);
		assertEquals(new_color.getString("name"), newName);

    }

	@Test
    public void shouldNotPostAnExistingColor() throws Exception {
		
        String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		
		URI uri = new URI("/api/colors/");

		JSONObject new_color = new JSONObject();
		new_color.put("name", nameColor);

        
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
		
		String messageError = "Já possui uma cor com esse nome";

		System.out.println("exception: " + exception.getMessage());
		
		assertTrue(exception.getMessage().contains(messageError));

    }

}
