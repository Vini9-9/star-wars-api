package com.vipdsilva.app.ws.model.response;

import java.util.HashMap;
import java.util.Map;


public class DeleteDtoResponseModel{
	
	private Map<String, String> warning;
	
	
	public DeleteDtoResponseModel(String msg) {
		this.warning = new HashMap<>();
		this.warning.put("message", msg);
	}
	

	public Map<String, String> getWarning() {
		return warning;
	}

	public void setWarning(Map<String, String> warning) {
		this.warning = warning;
	}

}
