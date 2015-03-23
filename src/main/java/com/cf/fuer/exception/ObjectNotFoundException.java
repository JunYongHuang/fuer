package com.cf.fuer.exception;


public class ObjectNotFoundException extends RuntimeException {

	/** The long serialVersionUID. */
	private static final long serialVersionUID = -2084368499338681516L;
	public ObjectNotFoundException() {
		super();
	}

	public ObjectNotFoundException(String message) {
		super(message);
	}
}
