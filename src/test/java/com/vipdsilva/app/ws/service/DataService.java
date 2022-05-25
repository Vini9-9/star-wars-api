package com.vipdsilva.app.ws.service;

import org.json.JSONObject;
import org.springframework.test.web.servlet.MvcResult;

public class DataService {

    public DataService() {
	}

	public JSONObject resultToJson(MvcResult result) throws Exception {
		String responseString = result.getResponse().getContentAsString();
		System.out.println("responseString: " + responseString);
		JSONObject json = new JSONObject(responseString);
		return json;
	}
    
}
