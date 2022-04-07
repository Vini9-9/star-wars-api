package com.vipdsilva.app.ws.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hair_color")
public class HairColor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_hair;
	
	@ManyToOne
    @JoinColumn(name = "people_id")
    private People people;
	
	@ManyToOne
	@JoinColumn(name = "colors_id")
	private Colors colors;

	public Integer getId_hair() {
		return id_hair;
	}

	public void setId_hair(Integer id_hair) {
		this.id_hair = id_hair;
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
