package de.othr.sw.cashbackplatform.exceptions;

public class CouponInvalidException extends Exception {
	
	private static final long serialVersionUID = -950771151803920155L;

	public CouponInvalidException(String errorMessage) {
		super(errorMessage);
	}
}
