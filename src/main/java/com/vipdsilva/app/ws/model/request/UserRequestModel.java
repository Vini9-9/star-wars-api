package com.vipdsilva.app.ws.model.request;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserRequestModel {
    
    private String name;
    private String email;
    private String password;

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
