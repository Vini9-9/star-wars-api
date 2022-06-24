package com.vipdsilva.app.ws.model.request;

import java.util.List;

public class UpdateUserRequestModel {
    
    private Long id;
    private String name;
	private String email;
	private String password;
	private List<String> profiles;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public List<String> getProfiles() {
        return profiles;
    }
    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    } 

}
