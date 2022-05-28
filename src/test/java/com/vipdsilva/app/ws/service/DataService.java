package com.vipdsilva.app.ws.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.test.web.servlet.MvcResult;

public class DataService {

    public DataService() {
	}

	public JSONObject getNewFilm() throws Exception{

		String[] listCharacters = {"R2-D2", "C-3PO"};
		JSONArray arrCharacters = new JSONArray(listCharacters);

		JSONObject new_film = new JSONObject();
		new_film.put("title", "Han Solo");
		new_film.put("director", "Ron Howard");
		new_film.put("producer",
		 "Kathleen Kennedy, Allison Shearmur, Simon Emanuel");
		new_film.put("release_date", "2018-05-25");
		new_film.put("characters", arrCharacters);

		return new_film;
	}

	public JSONObject getDataFilm() throws Exception {
		JSONObject data_film = new JSONObject();

		data_film.put("url", "/api/films/");
		data_film.put("idEFilm", 1);
		data_film.put("titleEFilm", "Uma Nova Esperança");
		data_film.put("idNEFilm", 99);

		return data_film;

	}

	public JSONObject resultToJson(MvcResult result) throws Exception {
		String responseString = result.getResponse().getContentAsString();
		System.out.println("responseString: " + responseString);
		JSONObject json = new JSONObject(responseString);
		return json;
	}
    
}
