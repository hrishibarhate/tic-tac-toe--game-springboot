package com.game.exception;

public class NotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public NotFoundException(String string) {
		this.message = string;
	}
	
	public String getMessage() {
		return message;
	}

}
