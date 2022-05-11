package com.vipdsilva.app.ws.entities;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;
import com.vipdsilva.app.ws.model.response.PeopleDtoResponseModel;
import com.vipdsilva.app.ws.repository.ColorsRepository;
import com.vipdsilva.app.ws.repository.FilmsRepository;
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
	private Set<HairColor> hairColor;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "people_id")
	private Set<SkinColor> skinColor;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "people_id")
	private Set<EyeColor> eyeColor;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "gender_ID")
	private Gender gender;
	
	@ManyToMany
	@JoinTable(
			name = "FilmsPeople",
			uniqueConstraints = @UniqueConstraint(columnNames = {"people_ID", "films_ID"}),
			joinColumns = @JoinColumn(name = "people_ID"),
			inverseJoinColumns = @JoinColumn(name = "films_ID")
			)
	private Set<Films> films;

	public People() {

	}

	public People(PeopleDtoRequestModel peopleReq, GenderRepository genderRepository,
			ColorsRepository colorsRepository, FilmsRepository filmsRepository) {

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

		Set<String> eye_colorReq = peopleReq.getEye_color();
		Set<String> skin_colorReq = peopleReq.getSkin_color();
		Set<String> hair_colorReq = peopleReq.getHair_color();
		Set<String> filmsReq = peopleReq.getFilms();
		
//		for (int i = 0; i < eye_colorReq.size(); i++) {
//
//			EyeColor corOlho = new EyeColor();
//
//			Colors color = colorsRepository.findByName(eye_colorReq.get(i));
//			corOlho.setColor(color);
//
//			this.setEyeColor(corOlho);
//
//		}
		
		Iterator<String> eyeAsIterator = eye_colorReq.iterator();
		Iterator<String> skinAsIterator = skin_colorReq.iterator();
		Iterator<String> hairAsIterator = hair_colorReq.iterator();
		Iterator<String> filmsAsIterator = filmsReq.iterator();
		
		while (eyeAsIterator.hasNext()) {

			EyeColor corOlho = new EyeColor();

			Colors color = colorsRepository.findByName(eyeAsIterator.next().toString());
			corOlho.setColor(color);

			this.setEyeColor(corOlho);

		}
		
		while (skinAsIterator.hasNext()) {

			SkinColor corPele = new SkinColor();

			Colors color = colorsRepository.findByName(skinAsIterator.next().toString());
			corPele.setColor(color);

			this.setSkinColor(corPele);

		}
		
		while (hairAsIterator.hasNext()) {

			HairColor corCabelo = new HairColor();

			Colors color = colorsRepository.findByName(hairAsIterator.next().toString());
			corCabelo.setColor(color);

			this.setHairColor(corCabelo);

		}
		
		while (filmsAsIterator.hasNext()) {
			Films film = filmsRepository.findByTitle(filmsAsIterator.next().toString());
			System.out.println("film: " + film.getTitle());
			this.setFilm(film);

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

	public Set<HairColor> getHairColors() {
		return hairColor;
	}

	public void setHairColors(Set<HairColor> hairColor) {
		this.hairColor = hairColor;
	}
	
	public void setHairColor(HairColor hairColor) {
		if (this.hairColor == null) {
			this.hairColor = new HashSet<HairColor>();
		}
		
		this.hairColor.add(hairColor);
		
	}

	
	public Set<Films> getFilms() {
		return films;
	}

	public void setFilms(Set<Films> films) {
		this.films = films;
	}
	
	public void setFilm(Films film) {
		if (this.films == null) {
			this.films = new HashSet<Films>();
		} 
		this.films.add(film);
	}

	public void clearFilms() {
		Set<Films> emptySet = Collections.emptySet();
		this.setFilms(emptySet);
	}

	public Set<SkinColor> getSkinColors() {
		return skinColor;
	}

	public void setSkinColors(Set<SkinColor> skinColor) {
		this.skinColor = skinColor;
	}
	
	
	public void setSkinColor(SkinColor skinColor) {
		if (this.skinColor == null) {
			this.skinColor = new HashSet<SkinColor>();
		}
		
		this.skinColor.add(skinColor);
	}


	public Set<EyeColor> getEyeColors() {
		return eyeColor;
	}


	public void setEyeColors(Set<EyeColor> eyeColor) {
		this.eyeColor = eyeColor;
	}

	public void setEyeColor(EyeColor eyeColor) {
		if (this.eyeColor == null) {
			this.eyeColor = new HashSet<EyeColor>();
		}
		
		this.eyeColor.add(eyeColor);
		
	}


	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Set<String> getEyeColorsName() {
		Set<String> cores = new HashSet<String>();
		
		Iterator<EyeColor> eyeAsIterator = this.getEyeColors().iterator();
		while (eyeAsIterator.hasNext()) {
			cores.add(eyeAsIterator.next().getColors().getName());
		}

		return cores;
	}

	public Set<String> getSkinColorsName() {
		Set<String> cores = new HashSet<String>();
		
		Iterator<SkinColor> skinAsIterator = this.getSkinColors().iterator();
		while (skinAsIterator.hasNext()) {
			cores.add(skinAsIterator.next().getColors().getName());
		}

		return cores;
	}

	public Set<String> getHairColorsName() {
		Set<String> cores = new HashSet<String>();
		
		Iterator<HairColor> hairAsIterator = this.getHairColors().iterator();
		while (hairAsIterator.hasNext()) {
			cores.add(hairAsIterator.next().getColors().getName());
		}

		return cores;
	}

	public Set<String> getFilmsTitle() {
		Set<String> titulos = new HashSet<String>();
		
		Iterator<Films> filmsAsIterator = this.getFilms().iterator();
		while (filmsAsIterator.hasNext()) {
			titulos.add(filmsAsIterator.next().getTitle());
		}

		return titulos;
	}

}
