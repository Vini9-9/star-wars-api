package com.vipdsilva.app.ws.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.vipdsilva.app.ws.model.request.FilmDtoRequestModel;
import com.vipdsilva.app.ws.model.response.FilmDtoResponseModel;
import com.vipdsilva.app.ws.repository.PeopleRepository;


@Entity
@Table(name = "films")
public class Films {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer episodeId;
	private String title;
	private String openingCrawl;
	private String director;
	private String producer;
	private String releaseDate;
	private Instant created;
	private Instant edited;
	
	@ManyToMany
	@JoinTable(
			name = "FilmsPeople",
			uniqueConstraints = @UniqueConstraint(columnNames = {"people_ID", "films_ID"}),
			joinColumns = @JoinColumn(name = "films_ID"),
			inverseJoinColumns = @JoinColumn(name = "people_ID")
			)
	private Set<People> characters;
	

	public Films() {
		
	}
	
	public Films (FilmDtoRequestModel filmReq, PeopleRepository peopleRepository) {
		this.id = filmReq.getId();
		this.episodeId = filmReq.getEpisode_id();
		this.title = filmReq.getTitle();
		this.openingCrawl = filmReq.getOpening_crawl();
		this.director = filmReq.getDirector();
		this.producer = filmReq.getProducer();
		this.releaseDate = filmReq.getRelease_date();
		Set<String> characterReq = filmReq.getCharacters();

		Iterator<String> characterAsIterator = characterReq.iterator();

		while (characterAsIterator.hasNext()) {
			People character = peopleRepository.findByName(characterAsIterator.next().toString());
			this.setCharacter(character);

		}
		
		if(this.getCreated() == null) {
			this.created = Instant.now();
		} else {
			this.edited = Instant.now();
		}
	}

	public FilmDtoResponseModel toResponseDto() {
		return new FilmDtoResponseModel(this);
	}
	
	public void setCharacter(People character) {
		if (this.characters == null) {
			this.characters = new HashSet<People>();
		} 
		this.characters.add(character);
	
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOpening_crawl() {
		return openingCrawl;
	}
	public void setOpening_crawl(String opening_crawl) {
		this.openingCrawl = opening_crawl;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getRelease_date() {
		return releaseDate;
	}
	public void setRelease_date(String release_date) {
		this.releaseDate = release_date;
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

	public Integer getEpisode_id() {
		return episodeId;
	}

	public void setEpisode_id(Integer episode_id) {
		this.episodeId = episode_id;
	}
	
	public Set<People> getCharacters() {
		return characters;
	}

	public Set<String> getCharactersName() {

		Set<String> nomes = new HashSet<String>();
		
		Iterator<People> peopleAsIterator = this.getCharacters().iterator();
		while (peopleAsIterator.hasNext()) {
			nomes.add(peopleAsIterator.next().getName());
		}

		return nomes;
		
	}

	public void setCharacters(Set<People> characters) {
		this.characters = characters;
	}

    public void clearCharacters() {
		this.characters = new HashSet<People>();
    }
	
}

