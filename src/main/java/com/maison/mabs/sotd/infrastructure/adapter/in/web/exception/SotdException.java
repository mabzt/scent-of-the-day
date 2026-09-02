package com.maison.mabs.sotd.infrastructure.adapter.in.web.exception;

public class SotdException extends RuntimeException {

	public SotdException(String message) {
		super(message);
	}

	public SotdException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
