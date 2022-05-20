package com.vipdsilva.app.ws.model.response;

import com.vipdsilva.app.ws.entities.Colors;
import com.vipdsilva.app.ws.entities.User;

import org.springframework.data.domain.Page;

public class UserDtoResponseModel {
   
    private Long id;
	private String name;
	private String email;

    
    public UserDtoResponseModel(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static Page<ColorsDtoResponseModel> convert(Page<Colors> colors) {
		return colors.map(ColorsDtoResponseModel::new);
	}

}
