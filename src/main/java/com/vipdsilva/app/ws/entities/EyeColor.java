package com.vipdsilva.app.ws.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "eye_color")
public class EyeColor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_eye;

	@ManyToOne
    @JoinColumn(name = "people_id")
    private People people;
	
	@ManyToOne
	@JoinColumn(name = "colors_id")
	private Colors colors;

	public Integer getId_eye() {
		return id_eye;
	}

	public void setId_eye(Integer id_eye) {
		this.id_eye = id_eye;
	}


	public Colors getColors() {
		return colors;
	}

	public void setColor(Colors colors) {
		this.colors = colors;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}
	
	

}
