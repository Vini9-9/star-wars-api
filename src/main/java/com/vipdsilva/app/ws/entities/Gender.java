package com.vipdsilva.app.ws.entities;

import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "gender")
public class Gender {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_gender;
	@NotBlank(message = "name não pode estar em branco")
	private String name;

	
	public Gender() {
	}
	
	public Gender(@NotBlank(message = "name não pode estar em branco") String name) {
		this.name = name;
		this.id_gender = ThreadLocalRandom.current().nextInt();
	}

	public Integer getId_gender() {
		return id_gender;
	}
	public void setId_gender(Integer id_gender) {
		this.id_gender = id_gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
