package com.vipdsilva.app.ws.model.response;

import java.time.Instant;
import java.util.Set;

import com.vipdsilva.app.ws.entities.Films;

import org.springframework.data.domain.Page;


public class FilmDtoResponseModel {

	private Integer id;
	private String title;
	private Integer episodeId;
	private String openingCrawl;
	private String director;
	private String producer;
	private String releaseDate;
	private Instant created;
	private Instant edited;
	private Set<String> characters;
	
	
	public FilmDtoResponseModel(Films film) {
		this.id = film.getId();
		this.title = film.getTitle() ;
		this.episodeId = film.getEpisode_id();
		this.openingCrawl = film.getOpening_crawl();
		this.director = film.getDirector();
		this.producer = film.getProducer();
		this.releaseDate = film.getRelease_date();
		this.created = film.getCreated();
		this.edited = film.getEdited();
		this.characters = film.getCharactersName();
    }

    public Integer getEpisode_id() {
		return episodeId;
	}

	public void setEpisode_id(Integer episode_id) {
		this.episodeId = episode_id;
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

    public static Page<FilmDtoResponseModel> converter(Page<Films> filmes) {
        return filmes.map(FilmDtoResponseModel::new);
    }

	
}
