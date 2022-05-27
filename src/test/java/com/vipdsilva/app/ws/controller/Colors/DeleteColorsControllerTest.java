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
public class DeleteColorsControllerTest {

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
	* Method: DELETE
	*/

	@Test
    public void shouldNotDeleteAColorWithoutAuth() throws Exception {
		Integer idEColor = 1;

        URI uri = new URI("/api/colors/" + idEColor);

       mockMvc
        .perform(MockMvcRequestBuilders
				.delete(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(403));
    }

	@Test
    public void shouldDeleteAColor() throws Exception {

		Integer totalColorsBefore = (int) colorsRepository.count();
        
		String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");

		Integer idEColor = 1;

        URI uri = new URI("/api/colors/" + idEColor);

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.delete(uri)
				.header("Authorization", tokenMod))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		

		JSONObject json = this.dataService.resultToJson(result); 

		Integer totalColorsAfter = (int) colorsRepository.count(); 
		String messageExpected = "Cor com id " + idEColor + " deletado com sucesso";
		String messageDelete = json.getJSONObject("warning").getString("message");
				
		assertEquals(totalColorsBefore-1, totalColorsAfter);
		assertEquals(messageExpected, messageDelete);

    }

	@Test
    public void shouldNotDeleteAnInexistingColor() throws Exception {
		
        String tokenMod = "Bearer " + this.authService.authAsModerador().getString("token");
		
		Integer idNEColor = 99;
		URI uri = new URI("/api/colors/" + idNEColor);

		MvcResult result = mockMvc
		.perform(MockMvcRequestBuilders
				.delete(uri)
				.header("Authorization", tokenMod))
				.andReturn();


		String messageError = "Cor com id " + idNEColor + " não localizada";

		JSONObject json = this.dataService.resultToJson(result);
		
		assertEquals(json.getString("message"), messageError);

    }

}
