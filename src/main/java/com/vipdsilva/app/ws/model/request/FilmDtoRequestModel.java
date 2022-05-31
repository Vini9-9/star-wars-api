package com.vipdsilva.app.ws.model.request;

import java.util.Set;

public class FilmDtoRequestModel {

	private Integer id;
	private Integer episode_id;
	private String title;
	private String opening_crawl;
	private String director;
	private String producer;
	private String release_date;
	private Set<String> characters;
	
	
	public void setId(int id) {
		this.id = id;
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
	public Integer getEpisode_id() {
		return episode_id;
	}
	public void setEpisode_id(Integer episode_id) {
		this.episode_id = episode_id;
	}
	
}
