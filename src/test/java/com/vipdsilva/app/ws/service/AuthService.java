package com.vipdsilva.app.ws.service;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


public class AuthService {

    private MockMvc mockMvc;

	private DataService dataService = new DataService();

	public AuthService(MockMvc mockMvc2) {
		this.mockMvc = mockMvc2;
	}

	/**
	* Method: Authetication as ROLE_ADMIN
	* Return: type of token and token
	*/
	public JSONObject authAsAdmin() throws Exception {

		URI uriAuth = new URI("/api/auth");

		JSONObject user_admin = new JSONObject();
		user_admin.put("email", "admin@email.com");
		user_admin.put("password", "admin");
		
		//System.out.println("Cheguei aqui: " + user_admin.toString());

		MvcResult result = this.mockMvc
        .perform(MockMvcRequestBuilders
				.post(uriAuth)
				.content(user_admin.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
				.andReturn();

		return dataService.resultToJson(result);
	}

    /**
	* Method: Authetication as ROLE_MODERADOR
	* Return: type of token and token
	*/
	public JSONObject authAsModerador() throws Exception {
		URI uriAuth = new URI("/api/auth");

		JSONObject user_mod = new JSONObject();
		user_mod.put("email", "moderador@email.com");
		user_mod.put("password", "moderador");

		MvcResult result = this.mockMvc
        .perform(MockMvcRequestBuilders
				.post(uriAuth)
				.content(user_mod.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		
		return dataService.resultToJson(result);
	}
    /**
	* Method: Authetication as ROLE_USUARIO
	* Return: type of token and token
	*/
	public JSONObject authAsUser() throws Exception {
		URI uriAuth = new URI("/api/auth");

		JSONObject user_mod = new JSONObject();
		user_mod.put("email", "usuario@email.com");
		user_mod.put("password", "usuario");

		MvcResult result = this.mockMvc
        .perform(MockMvcRequestBuilders
				.post(uriAuth)
				.content(user_mod.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		
		return dataService.resultToJson(result);
	}

    /**
	* Method: Authetication as ROLE_USUARIO
	* Return: MvcResult
	*/
	public MvcResult authAsNewUser(String email, String password) throws Exception {
		URI uriAuth = new URI("/api/auth");

		JSONObject user = new JSONObject();
		user.put("email", email);
		user.put("password", password);

		MvcResult result = this.mockMvc
        .perform(MockMvcRequestBuilders
				.post(uriAuth)
				.content(user.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		return result;
	}

	public String getToken(JSONObject json) throws JSONException {
		return "Bearer " + json.getString("token");
	}
    
}
