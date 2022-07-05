package com.vipdsilva.app.ws.controller.service;

import org.json.JSONArray;
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

	public JSONObject getNewPeople() throws Exception{

		String[] listFilms = {
			// "A Ameaça Fantasma",
			// "A Vingança dos Sith",
			// "O império Contra-ataca",
			// "O retorno de Jedi",
			// "Uma Nova Esperança",
			"Ataque dos Clones"
		};
		JSONArray arrFilms = new JSONArray(listFilms);

		JSONObject new_people = new JSONObject();
		new_people.put("name", "Obi-Wan Kenobi");
		new_people.put("birth_year", "57BBY");
		new_people.put("height", 182);
		new_people.put("mass", 77);
		new_people.put("hair_color", "preto");
		new_people.put("skin_color", "branco");
		new_people.put("eye_color", "azul");
		new_people.put("gender", "masculino");
		new_people.put("films", arrFilms);

		return new_people;
	}

	public JSONObject getNewEPeople() throws Exception{

		String[] listFilms = {
			"A Ameaça Fantasma",
			"A Vingança dos Sith",
			"O império Contra-ataca",
			"O retorno de Jedi",
			"Uma Nova Esperança",
			"Ataque dos Clones"
		};
		JSONArray arrFilms = new JSONArray(listFilms);

		JSONObject new_person = new JSONObject();
		new_person.put("name", "Luke Skywalker");
		new_person.put("birth_year", "57BBY");
		new_person.put("height", 182);
		new_person.put("mass", 77);
		new_person.put("hair_color", "preto");
		new_person.put("skin_color", "branco");
		new_person.put("eye_color", "azul");
		new_person.put("gender", "masculino");
		new_person.put("films", arrFilms);

		return new_person;
	}

	public JSONObject getDataFilm() throws Exception {
		JSONObject data_film = new JSONObject();

		data_film.put("url", "/api/films/");
		data_film.put("idEFilmGET", 3);
		data_film.put("idEFilmDELETE", 2);
		data_film.put("idEFilm", 1);
		data_film.put("titleEFilm", "Uma Nova Esperança");
		data_film.put("titleEFilmGET", "O retorno de Jedi");
		data_film.put("idNEFilm", 99);

		return data_film;

	}
	
	public JSONObject getDataColor() throws Exception {
		JSONObject data_color = new JSONObject();

		data_color.put("url", "/api/colors/");
		data_color.put("idEColor", 1);
		data_color.put("nameEColor", "branco");
		data_color.put("idNEColor", 99);
		data_color.put("nameEColorPOST", "azul");
		data_color.put("newColorNamePOST", "Laranja");
		data_color.put("newColorNamePUT", "verde limão");

		return data_color;

	}

	public JSONObject getDataPeople() throws Exception {
		JSONObject data_people = new JSONObject();

		data_people.put("url", "/api/people/");
		data_people.put("idEPeople", 1);
		data_people.put("idEPeopleDELETE", 2);
		data_people.put("nameEPeople", "Luke Skywalker");
		data_people.put("idNEPeople", 99);
		data_people.put("newPeopleNamePOST", "Neymar");
		data_people.put("newPeopleNamePUT", "Cristiano");

		return data_people;

	}

	public JSONObject resultToJson(MvcResult result) throws Exception {
		String responseString = result.getResponse().getContentAsString();
		System.out.println("responseString: " + responseString);
		JSONObject json = new JSONObject(responseString);
		return json;
	}
    
}
