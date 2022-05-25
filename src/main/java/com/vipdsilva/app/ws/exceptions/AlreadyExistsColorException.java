package com.vipdsilva.app.ws.exceptions;

public class AlreadyExistsColorException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public AlreadyExistsColorException(String atributte)
	{
		super("Já possui uma cor com esse " + atributte);
	}
}
