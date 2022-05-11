package com.vipdsilva.app.ws.entities;

import java.time.Instant;
import java.util.List;
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
import com.vipdsilva.app.ws.model.request.PeopleDtoRequestModel;


@Entity
@Table(name = "films")
public class Films {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer episode_id;
	private String title;
	private String opening_crawl;
	private String director;
	private String producer;
	private String release_date;
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
	
	public Films (FilmDtoRequestModel filmReq) {
		this.id = filmReq.getId();
		this.episode_id = filmReq.getEpisode_id();
		this.title = filmReq.getTitle();
		this.opening_crawl = filmReq.getOpening_crawl();
		this.director = filmReq.getDirector();
		this.producer = filmReq.getProducer();
		this.release_date = filmReq.getRelease_date();
		
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

	public Integer getEpisode_id() {
		return episode_id;
	}

	public void setEpisode_id(Integer episode_id) {
		this.episode_id = episode_id;
	}
	
	
	
}

