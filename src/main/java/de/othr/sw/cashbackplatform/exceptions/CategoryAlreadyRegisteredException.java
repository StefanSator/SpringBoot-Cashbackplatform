package de.othr.sw.cashbackplatform.exceptions;

public class CategoryAlreadyRegisteredException extends Exception {

	private static final long serialVersionUID = 3065950534270854275L;

	public CategoryAlreadyRegisteredException(String errorMessage) {
		super(errorMessage);
	}
}
