package com.vipdsilva.app.ws.model.response;

import com.vipdsilva.app.ws.entities.People;

public class PeopleDtoResponseModel {

	private Integer id;
	private String name;
	private Integer height;
	private Integer mass;
	private String birth_year;
//	private String hair_color;
//	private String skin_color;
//	private String eye_color;
	private String gender;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getMass() {
		return mass;
	}
	public void setMass(Integer mass) {
		this.mass = mass;
	}
	public String getBirth_year() {
		return birth_year;
	}
	public void setBirth_year(String birth_year) {
		this.birth_year = birth_year;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public PeopleDtoResponseModel(People people) {
		this.id = people.getId();
		this.name = people.getName() ;
		this.height = people.getHeight();
		this.mass = people.getMass();
		this.birth_year = people.getBirth_year();
		this.gender = people.getGender().getName();
	}
	
}
