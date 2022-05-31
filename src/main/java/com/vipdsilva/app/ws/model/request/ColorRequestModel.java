package com.vipdsilva.app.ws.model.request;

public class ColorRequestModel {
    
    private String name;

    public ColorRequestModel() {
        
    }
    
    public ColorRequestModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
