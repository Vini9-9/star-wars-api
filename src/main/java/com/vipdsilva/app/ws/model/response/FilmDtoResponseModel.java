package com.vipdsilva.app.ws.model.response;

import java.time.Instant;
import java.util.Set;

import com.vipdsilva.app.ws.entities.Films;


public class FilmDtoResponseModel {

	private Integer id;
	private String title;
	private String opening_crawl;
	private String director;
	private String producer;
	private String release_date;
	private Instant created;
	private Instant edited;
	private Set<String> characters;
	
	
	public FilmDtoResponseModel(Films film) {
		this.id = film.getId();
		this.title = film.getTitle() ;
		this.opening_crawl = film.getOpening_crawl();
		this.director = film.getDirector();
		this.producer = film.getProducer();
		this.release_date = film.getRelease_date();
		this.created = film.getCreated();
		this.edited = film.getEdited();
		this.characters = film.getCharactersName();
    }

    public Integer getId() {
		return id;
	}
	public Set<String> getCharacters() {
		return characters;
	}
	public void setCharacters(Set<String> characters) {
		this.characters = characters;
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
		return opening_crawl;
	}
	public void setOpening_crawl(String opening_crawl) {
		this.opening_crawl = opening_crawl;
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
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
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

	
}
