package com.vipdsilva.app.ws.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "colors")
public class Colors {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@OneToMany
	@JoinColumn(name = "colors_id")
	private List<EyeColor> eyeColor;
	
	@OneToMany
	@JoinColumn(name = "colors_id")
	private List<SkinColor> skinColor;
	
	@OneToMany
	@JoinColumn(name = "colors_id")
	private List<HairColor> hairColor;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
