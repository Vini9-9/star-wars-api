package com.vipdsilva.app.ws.service;

import java.net.URI;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
public class DataService {

    @Autowired
    private MockMvc mockMvc;

    public DataService() {
	}
	
    public DataService(MockMvc mockMvc2) {
		this.mockMvc = mockMvc2;
	}

	/**
	* Method: GET
	* Return: JSONObject with all colors data as pageable
	*/
	public JSONObject getAllColors() throws Exception {
		URI uri = new URI("/api/colors/");

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
				.get(uri))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
				.andReturn();
		
		return resultToJson(result);
	}

	public Integer getTotalColors() throws Exception {
		JSONObject jsonAllColors = getAllColors();

		Integer numberOfElements = jsonAllColors.getInt("numberOfElements");
		
		return numberOfElements;
	}

	public JSONObject resultToJson(MvcResult result) throws Exception {
		String responseString = result.getResponse().getContentAsString();
		System.out.println("responseString: " + responseString);
		JSONObject json = new JSONObject(responseString);
		return json;
	}
    
}
