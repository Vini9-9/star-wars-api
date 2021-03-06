package com.vipdsilva.app.ws.exceptions;

import java.util.Date;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.vipdsilva.app.ws.model.response.ErrorMessage;


@ControllerAdvice
public class AppExceptionsHandler {		
		//EntityNotFoundException
		
		@ExceptionHandler(value = { NotFoundException.class })
	    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
	        
	    	String errorMessageDescription = ex.getLocalizedMessage();
	    	
	    	if(errorMessageDescription == null) errorMessageDescription = ex.toString();
	    	
	    	ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
	    	
	    	return new ResponseEntity<>(
	    			errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
	    	
	    }
	
	
}
