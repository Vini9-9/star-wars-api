package com.vipdsilva.app.ws.model.response;

import com.vipdsilva.app.ws.entities.Colors;

import org.springframework.data.domain.Page;

public class ColorsDtoResponseModel {
   
    private Integer id;
	private String name;

    
    public ColorsDtoResponseModel(Colors colors) {
        this.id = colors.getId();
        this.name = colors.getName();
    }


    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static Page<ColorsDtoResponseModel> convert(Page<Colors> colors) {
		return colors.map(ColorsDtoResponseModel::new);
	}

}
