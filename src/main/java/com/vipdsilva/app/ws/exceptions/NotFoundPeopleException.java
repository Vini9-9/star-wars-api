package com.vipdsilva.app.ws.exceptions;


public class NotFoundPeopleException extends NotFoundException{
	
	private static final long serialVersionUID = 1L;

	public NotFoundPeopleException(Integer peopleId)
	{
		super("Pessoa com id " + peopleId + " não localizada");
	}

	public NotFoundPeopleException()
	{
		super("Nenhuma pessoa cadastrada");
	}
}
