package es.ucm.fdi.tp.exception;

import es.ucm.fdi.tp.base.model.GameError;

public class NotValidArgumentException extends GameError{
	public NotValidArgumentException(String s){
		super(s);
	}
}
