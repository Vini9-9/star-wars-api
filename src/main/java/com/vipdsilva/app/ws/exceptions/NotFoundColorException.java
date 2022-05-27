package com.vipdsilva.app.ws.exceptions;


public class NotFoundColorException extends NotFoundException{
	
	private static final long serialVersionUID = 1L;

	public NotFoundColorException(Integer colorId)
	{
		super("Cor com id " + colorId + " não localizada");
	}

	public NotFoundColorException()
	{
		super("Nenhuma cor cadastrada");
	}
}
