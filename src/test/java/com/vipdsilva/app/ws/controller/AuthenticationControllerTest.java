package com.vipdsilva.app.ws.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vipdsilva.app.ws.ApplicationConfigTest;
import com.vipdsilva.app.ws.controller.service.AuthService;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

public class AuthenticationControllerTest extends ApplicationConfigTest {

    @Autowired
    private MockMvc mockMvc;

    private AuthService authService;

	@BeforeEach
	private void initEach() {
		this.authService = new AuthService(mockMvc);
   }


    @Test
    public void shouldAuthAnUser() throws Exception {
        
        JSONObject actualJson = this.authService.authAsUser();

        assertTrue(actualJson.has("token"));

    }

    @Test
    public void shouldAuthAnModerador() throws Exception {
        
        JSONObject actualJson = this.authService.authAsModerador();

        assertTrue(actualJson.has("token"));

    }

    @Test
    public void shouldAuthAnAdmin() throws Exception {
        
        JSONObject actualJson = this.authService.authAsAdmin();

        assertTrue(actualJson.has("token"));

    }

    @Test
    public void shouldNotAuthAnInexistingUser() throws Exception {

        MvcResult mvcResult = this.authService
         .authAsNewUser("teste@email.com", "teste");

        assertEquals(400, mvcResult.getResponse().getStatus());

    }
}
