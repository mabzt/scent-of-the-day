package com.maison.mabs.sotd.utils;

import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Clouds;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.CurrentWeather;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Weather;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.WeatherMain;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Wind;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@UtilityClass
public class OpenWeatherTestDataUtil {

	public CurrentWeather validWeather() {
		return CurrentWeather.builder()
			.weather(List.of(weather()))
			.main(weatherMain())
			.wind(wind())
			.clouds(clouds())
			.observedAt(Instant.now())
			.build();
	}

	public CurrentWeather inValidWeather() {
		return CurrentWeather.builder()
			.weather(List.of(weather()))
			.main(weatherMain())
			.wind(wind())
			.clouds(clouds())
			.observedAt(Instant.now())
			.build();
	}

	public WeatherMain weatherMain() {
		return WeatherMain.builder()
			.temperature(BigDecimal.valueOf(11.15))
			.feelsLike(BigDecimal.valueOf(10.83))
			.minimumTemperature(BigDecimal.valueOf(10.56))
			.maximumTemperature(BigDecimal.valueOf(11.83))
			.humidity(96)
			.build();
	}

	public WeatherMain invalidWeatherMain() {
		return WeatherMain.builder()
			.temperature(BigDecimal.ZERO)
			.feelsLike(BigDecimal.ZERO)
			.minimumTemperature(null)
			.maximumTemperature(null)
			.humidity(0)
			.build();
	}

	public Wind wind() {
		return Wind.builder().direction(12).speed(BigDecimal.valueOf(34, 3)).build();
	}

	public Clouds clouds() {
		return Clouds.builder().coverage(32).build();
	}

	public Weather weather() {
		return Weather.builder().main("Rain").description("light rain").build();

	}

}
