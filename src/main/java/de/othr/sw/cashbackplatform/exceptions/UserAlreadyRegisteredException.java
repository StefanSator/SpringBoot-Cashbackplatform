package de.othr.sw.cashbackplatform.exceptions;

public class UserAlreadyRegisteredException extends Exception {

	private static final long serialVersionUID = 3065950534270854275L;

	public UserAlreadyRegisteredException(String errorMessage) {
		super(errorMessage);
	}
}
