package com.maison.mabs.sotd.infrastructure.adapter.out.client.openweather.exception;

public class OpenWeatherException extends RuntimeException {

	public OpenWeatherException(String message) {
		super(message);
	}

	public OpenWeatherException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
