package com.vipdsilva.app.ws.exceptions;

public class AlreadyExistsFilmException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public AlreadyExistsFilmException(String atributte)
	{
		super("Já possui um filme com esse " + atributte);
	}
}
