package com.vipdsilva.app.ws.entities;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;

@Entity
@Table(name = "people")
public class People {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer height;
	private Integer mass;
	private String birth_year;
	private Instant created;
	private Instant edited;
	
	@OneToMany
	@JoinColumn(name = "people_id")
	private List<HairColor> hairColor;
	
	@OneToMany
	@JoinColumn(name = "people_id")
	private List<SkinColor> skinColor;
	
	@OneToMany
	@JoinColumn(name = "people_id")
	private List<EyeColor> eyeColor;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gender_ID")
	private Gender gender;
	
	public People() {
		
	}
	
	public People(Integer id, String name, Integer height, Integer mass, String birth_year) {
		this.id = id;
		this.name = name;
		this.height = height;
		this.mass = mass;
		this.birth_year = birth_year;
		
		if(this.getCreated() == null) {
			this.created = Instant.now();
		} else {
			this.edited = Instant.now();
		}
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
	public Instant getCreated() {
		return created;
	}
	public void setCreated(Instant created) {
		this.created = created;
	}
	public Instant getEdited() {
		return edited;
	}
	public void setEdited(Instant edited) {
		this.edited = edited;
	}

	public PeopleDtoResponseModel toResponseDto() {
		
		return new PeopleDtoResponseModel(
				this.id,
				this.name,
				this.height,
				this.mass,
				this.birth_year
				);
	}

	
	
}
