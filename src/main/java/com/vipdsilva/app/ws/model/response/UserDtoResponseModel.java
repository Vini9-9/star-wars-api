package com.vipdsilva.app.ws.model.response;

import java.util.ArrayList;
import java.util.List;

import com.vipdsilva.app.ws.entities.User;

public class UserDtoResponseModel {
   
    private Long id;
	private String name;
	private String email;
	private List<String> profiles =  new ArrayList<>();

    
    public UserDtoResponseModel(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profiles = user.getProfilesName(user.getProfiles());
    }


    public List<String> getProfiles() {
        return profiles;
    }


    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
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

}
