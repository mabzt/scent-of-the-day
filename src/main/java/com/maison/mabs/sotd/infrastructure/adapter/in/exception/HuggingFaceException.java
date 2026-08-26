package com.maison.mabs.sotd.infrastructure.adapter.in.exception;

public class HuggingFaceException extends RuntimeException {

	public HuggingFaceException(String message) {
		super(message);
	}

	public HuggingFaceException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
