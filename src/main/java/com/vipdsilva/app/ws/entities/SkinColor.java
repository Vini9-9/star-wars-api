package com.vipdsilva.app.ws.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "skin_color")
public class SkinColor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_skin;
	
	@ManyToOne
    @JoinColumn(name = "people_id")
    private People people;
	
	@ManyToOne
	@JoinColumn(name = "colors_id")
	private Colors colors;

	public Integer getId_skin() {
		return id_skin;
	}

	public void setId_skin(Integer id_skin) {
		this.id_skin = id_skin;
	}


	public Colors getColors() {
		return colors;
	}

	public void setColors(Colors colors) {
		this.colors = colors;
	}

}
