package com.vipdsilva.app.ws.exceptions;


public class NotFoundFilmException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NotFoundFilmException(Integer filmId)
	{
		super("Filme com id " + filmId + " não localizado");
	}
}
