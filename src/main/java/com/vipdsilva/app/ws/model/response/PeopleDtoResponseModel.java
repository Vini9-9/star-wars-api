package com.vipdsilva.app.ws.model.response;

import java.util.ArrayList;
import java.util.List;

import com.vipdsilva.app.ws.entities.EyeColor;
import com.vipdsilva.app.ws.entities.HairColor;
import com.vipdsilva.app.ws.entities.People;
import com.vipdsilva.app.ws.entities.SkinColor;

public class PeopleDtoResponseModel {

	private Integer id;
	private String name;
	private Integer height;
	private Integer mass;
	private String birth_year;
	private List<String> hair_color = new ArrayList<String>();
	private List<String> skin_color = new ArrayList<String>();
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
		this.skin_color = people.getSkinColorsName();
		this.hair_color = people.getHairColorsName();
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
	
	public List<String> getHair_colors() {
		return hair_color;
	}


	public void setHair_color(List<String> hair_color) {
		this.hair_color = hair_color;
	}


	public List<String> getSkin_colors() {
		return skin_color;
	}


	public void setSkin_color(List<String> skin_color) {
		this.skin_color = skin_color;
	}


	public void setEye_colors(List<String> eye_color) {
		this.eye_color = eye_color;
	}
	
	public void setEye_color(EyeColor eye_color) {
		if(this.eye_color != null) {
			this.eye_color.add(eye_color.getColors().getName());
		} 
	}

	public void setSkin_color(SkinColor skin_color) {
		if(this.skin_color != null) {
			this.skin_color.add(skin_color.getColors().getName()) ;
		}	
	}
	
	public void setHair_color(HairColor hair_color) {
		if(this.hair_color != null) {
			this.hair_color.add(hair_color.getColors().getName()) ;
		}
		
	}
	
}
