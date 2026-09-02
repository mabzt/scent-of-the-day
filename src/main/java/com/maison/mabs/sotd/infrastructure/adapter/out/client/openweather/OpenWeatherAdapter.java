package com.maison.mabs.sotd.infrastructure.adapter.out.client.openweather;

import com.maison.mabs.sotd.application.port.out.OpenWeatherPort;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.CurrentWeather;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Location;
import com.maison.mabs.sotd.infrastructure.config.openweather.OpenWeatherConfigProperties;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenWeatherAdapter implements OpenWeatherPort {

	private static final String METRIC = "metric";

	private final OpenWeatherConfigProperties configProperties;

	private final OpenWeatherClientApi weatherClientApi;

	@Override
	@Retry(name = "generic-service-resillience", fallbackMethod = "getLocationFallback")
	public List<Location> getLocation(@NonNull String city) {
		return this.weatherClientApi.geoCodeCity(city, this.configProperties.limit(), this.configProperties.apiKey());
	}

	@Override
	@Retry(name = "generic-service-resillience", fallbackMethod = "getCurrentWeatherFallback")
	public CurrentWeather getCurrentWeather(@NonNull BigDecimal longitude, @NonNull BigDecimal latitude) {
		return this.weatherClientApi.getCurrentWeather(latitude, longitude, this.configProperties.apiKey(), METRIC);
	}

	private List<Location> getLocationFallback(String city, Throwable throwable) {
		log.warn("Failed to geo code city: {} with exception: [{}]", city, throwable.getMessage());
		return null;
	}

	private CurrentWeather getCurrentWeatherFallback(BigDecimal longitude, @NonNull BigDecimal latitude,
			Throwable throwable) {
		log.warn("Failed to retrieve current weather at  longitude: {} latitude: {} with exception: [{}] ", longitude,
				latitude, throwable.getMessage());
		return null;
	}

}
