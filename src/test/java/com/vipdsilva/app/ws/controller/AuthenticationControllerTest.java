package com.vipdsilva.app.ws.controller;

import java.net.URI;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void shouldAuthAnUser() throws Exception {
        URI uri = new URI("/api/auth");
        JSONObject my_obj = new JSONObject();

		my_obj.put("email", "admin@email.com");
		my_obj.put("password", "admin");

        mockMvc
        .perform(MockMvcRequestBuilders
                .post(uri)
                .content(my_obj.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
				.status()
				.is(200));

    }

    @Test
    public void shouldNotAuthAnUser() throws Exception {
        URI uri = new URI("/api/auth");
        JSONObject my_obj = new JSONObject();

		my_obj.put("email", "user@email.com");
		my_obj.put("password", "user");

        mockMvc
        .perform(MockMvcRequestBuilders
                .post(uri)
                .content(my_obj.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
				.status()
				.is(400));

    } 
}
