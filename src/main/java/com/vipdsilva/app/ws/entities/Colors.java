package com.vipdsilva.app.ws.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "colors")
public class Colors {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@OneToMany
	@JoinColumn(name = "colors_id")
	private List<EyeColor> eyeColor;
	
	@OneToMany
	@JoinColumn(name = "colors_id")
	private List<SkinColor> skinColor;
	
	@OneToMany
	@JoinColumn(name = "colors_id")
	private List<HairColor> hairColor;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<EyeColor> getEyeColor() {
		return eyeColor;
	}
	public void setEyeColor(List<EyeColor> eyeColor) {
		this.eyeColor = eyeColor;
	}
	public List<SkinColor> getSkinColor() {
		return skinColor;
	}
	public void setSkinColor(List<SkinColor> skinColor) {
		this.skinColor = skinColor;
	}
	public List<HairColor> getHairColor() {
		return hairColor;
	}
	public void setHairColor(List<HairColor> hairColor) {
		this.hairColor = hairColor;
	}
	
	

}
