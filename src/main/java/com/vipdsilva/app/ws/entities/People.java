package com.vipdsilva.app.ws.entities;

import java.time.Instant;
import java.util.ArrayList;
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
import com.vipdsilva.app.ws.repository.ColorsRepository;
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

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "people_id")
	private List<HairColor> hairColor;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "people_id")
	private List<SkinColor> skinColor;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "people_id")
	private List<EyeColor> eyeColor;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gender_ID")
	private Gender gender;

	public People() {

	}

	public People(PeopleDtoRequestModel peopleReq, GenderRepository genderRepository,
			ColorsRepository colorsRepository) {

		this.id = peopleReq.getId();
		this.name = peopleReq.getName();
		this.height = peopleReq.getHeight();
		this.mass = peopleReq.getMass();
		this.birth_year = peopleReq.getBirth_year();
		this.gender = genderRepository.findByName(peopleReq.getGender());

		if (this.getCreated() == null) {
			this.created = Instant.now();
		} else {
			this.edited = Instant.now();
		}

		List<String> eye_colorReq = peopleReq.getEye_color();
		List<String> skin_colorReq = peopleReq.getSkin_color();
		List<String> hair_colorReq = peopleReq.getHair_color();

		for (int i = 0; i < eye_colorReq.size(); i++) {

			EyeColor corOlho = new EyeColor();

			Colors color = colorsRepository.findByName(eye_colorReq.get(i));
			corOlho.setColor(color);

			this.setEyeColor(corOlho);

		}

		for (int i = 0; i < skin_colorReq.size(); i++) {

			SkinColor corPele = new SkinColor();

			Colors color = colorsRepository.findByName(skin_colorReq.get(i));
			corPele.setColor(color);

			this.setSkinColor(corPele);
			

		}

		for (int i = 0; i < hair_colorReq.size(); i++) {

			HairColor corCabelo = new HairColor();

			Colors color = colorsRepository.findByName(hair_colorReq.get(i));
			corCabelo.setColor(color);

			this.setHairColor(corCabelo);

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

	public List<HairColor> getHairColors() {
		return hairColor;
	}

	public void setHairColors(List<HairColor> hairColor) {
		this.hairColor = hairColor;
	}
	
	public void setHairColor(HairColor hairColor) {
		if (this.hairColor == null) {
			this.hairColor = new ArrayList<HairColor>();
		}
		if (hairColor != null) {

			this.hairColor.add(hairColor);

		}
	}

	public HairColor getHairColor(Integer index) {
		return this.hairColor.get(index);
	}

	public List<SkinColor> getSkinColors() {
		return skinColor;
	}

	public void setSkinColors(List<SkinColor> skinColor) {
		this.skinColor = skinColor;
	}
	
	public void setSkinColor(SkinColor skinColor) {
		if (this.skinColor == null) {
			this.skinColor = new ArrayList<SkinColor>();
		}
		if (skinColor != null) {

			this.skinColor.add(skinColor);

		}
	}

	public SkinColor getSkinColor(Integer index) {
		return this.skinColor.get(index);
	}

	public List<EyeColor> getEyeColors() {
		return eyeColor;
	}

	public EyeColor getEyeColor(Integer index) {
		return this.eyeColor.get(index);
	}

	public List<String> getEyeColorsName() {
		List<String> cores = new ArrayList<String>();
		for (int i = 0; i < this.getEyeColors().size(); i++) {
			System.out.println("ec: " + getEyeColor(i).getColors().getName());
			cores.add(getEyeColor(i).getColors().getName());
		}

		return cores;
	}

	public void setEyeColors(List<EyeColor> eyeColor) {
		this.eyeColor = eyeColor;
	}

	public void setEyeColor(EyeColor eyeColor) {
		if (this.eyeColor == null) {
			this.eyeColor = new ArrayList<EyeColor>();
		}
		if (eyeColor != null) {

			this.eyeColor.add(eyeColor);

		}
	}

	public List<String> getSkinColorsName() {
		List<String> cores = new ArrayList<String>();
		for (int i = 0; i < this.getSkinColors().size(); i++) {
			System.out.println("sc: " + getSkinColor(i).getColors().getName());
			cores.add(getSkinColor(i).getColors().getName());
		}

		return cores;
	}

	public List<String> getHairColorsName() {
		List<String> cores = new ArrayList<String>();
		for (int i = 0; i < this.getHairColors().size(); i++) {
			System.out.println("hc: " + getSkinColor(i).getColors().getName());
			cores.add(getHairColor(i).getColors().getName());
		}

		return cores;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
