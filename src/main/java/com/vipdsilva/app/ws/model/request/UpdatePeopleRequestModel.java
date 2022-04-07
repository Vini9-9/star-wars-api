package com.vipdsilva.app.ws.model.request;

import java.util.List;

public class UpdatePeopleRequestModel {

	private Integer id;
	private String name;
	private Integer height;
	private Integer mass;
	private String birth_year;
	private String gender;
	private List<String> hair_color;
	private List<String> skin_color;
	private List<String> eye_color;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<String> getHair_color() {
		return hair_color;
	}
	public void setHair_color(List<String> hair_color) {
		this.hair_color = hair_color;
	}
	public List<String> getSkin_color() {
		return skin_color;
	}
	public void setSkin_color(List<String> skin_color) {
		this.skin_color = skin_color;
	}
	public List<String> getEye_color() {
		return eye_color;
	}
	public void setEye_color(List<String> eye_color) {
		this.eye_color = eye_color;
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
}
