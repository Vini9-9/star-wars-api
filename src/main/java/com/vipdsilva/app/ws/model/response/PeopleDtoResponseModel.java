package com.vipdsilva.app.ws.model.response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.vipdsilva.app.ws.entities.EyeColor;
import com.vipdsilva.app.ws.entities.People;

public class PeopleDtoResponseModel {

	private Integer id;
	private String name;
	private Integer height;
	private Integer mass;
	private String birth_year;
//	private String hair_color;
//	private String skin_color;
	private List<String> eye_color = new ArrayList<String>();
	private String gender;
	
	public PeopleDtoResponseModel(People people) {
		this.id = people.getId();
		this.name = people.getName() ;
		this.height = people.getHeight();
		this.mass = people.getMass();
		this.birth_year = people.getBirth_year();
		this.gender = people.getGender().getName();
		this.eye_color = people.getEyeColorsName();
	}
	
	
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

	public List<String> getEye_colors() {
		return eye_color;
	}
	
	public void setEye_colors(List<String> eye_color) {
		this.eye_color = eye_color;
	}
	
	public void setEye_color(EyeColor eye_color) {
		//System.out.println("eye_color_people_name: " + eye_color.getPeople().getName());
		if(this.eye_color != null) {
			//System.out.println("eye_color_name: " + eye_color.getColors().getName());
			this.eye_color.add(eye_color.getColors().getName());
		} 
		//System.out.println("this.eye_color: " + this.eye_color);
	}
	
}
