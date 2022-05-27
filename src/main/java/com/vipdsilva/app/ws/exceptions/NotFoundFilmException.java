package com.vipdsilva.app.ws.exceptions;


public class NotFoundFilmException extends NotFoundException{
	
	private static final long serialVersionUID = 1L;

	public NotFoundFilmException(Integer filmId)
	{
		super("Filme com id " + filmId + " não localizado");
	}

	public NotFoundFilmException()
	{
		super("Nenhum filme cadastrado");
	}
}
