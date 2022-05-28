package com.vipdsilva.app.ws.exceptions;

public class AlreadyExistsPersonException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public AlreadyExistsPersonException(String atributte)
	{
		super("Já possui uma pessoa com esse " + atributte);
	}
}
