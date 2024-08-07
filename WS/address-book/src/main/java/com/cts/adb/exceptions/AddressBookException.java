package com.cts.adb.exceptions;

public class AddressBookException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddressBookException() {
		super();
	}

	public AddressBookException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AddressBookException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddressBookException(String message) {
		super(message);
	}

	public AddressBookException(Throwable cause) {
		super(cause);
	}

	
}
