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

import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.GenderRepository;

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
	
	public People(PeopleDtoRequestModel peopleReq, GenderRepository genderRepository) {
		this.id = peopleReq.getId();
		this.name = peopleReq.getName();
		this.height = peopleReq.getHeight();
		this.mass = peopleReq.getMass();
		this.birth_year = peopleReq.getBirth_year();
		this.gender = genderRepository.findByName(peopleReq.getGender());
		
		if(this.getCreated() == null) {
			this.created = Instant.now();
		} else {
			this.edited = Instant.now();
		}
	}
	
	public PeopleDtoResponseModel toResponseDto() {
		return new PeopleDtoResponseModel(this);
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
	
	public List<HairColor> getHairColor() {
		return hairColor;
	}

	public void setHairColor(List<HairColor> hairColor) {
		this.hairColor = hairColor;
	}

	public List<SkinColor> getSkinColor() {
		return skinColor;
	}

	public void setSkinColor(List<SkinColor> skinColor) {
		this.skinColor = skinColor;
	}

	public List<EyeColor> getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(List<EyeColor> eyeColor) {
		this.eyeColor = eyeColor;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	
}
