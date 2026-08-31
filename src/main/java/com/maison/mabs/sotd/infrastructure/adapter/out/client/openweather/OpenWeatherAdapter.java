package com.maison.mabs.sotd.infrastructure.adapter.out.client.openweather;

import com.maison.mabs.sotd.application.port.out.OpenWeatherPort;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.CurrentWeather;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Location;
import com.maison.mabs.sotd.infrastructure.config.openweather.OpenWeatherConfigProperties;
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
	public List<Location> getLocation(@NonNull String city) {
		// Todo: Validate location response
		return this.weatherClientApi.geoCodeCity(city, this.configProperties.limit(), this.configProperties.apiKey());
	}

	@Override
	public CurrentWeather getCurrentWeather(@NonNull BigDecimal longitude, @NonNull BigDecimal latitude) {
		// Todo: Validate Response
		return this.weatherClientApi.getCurrentWeather(latitude, longitude, this.configProperties.apiKey(), METRIC);
	}

}
