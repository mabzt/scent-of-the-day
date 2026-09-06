package com.maison.mabs.sotd.application.port.out;

import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.CurrentWeather;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Location;

import java.math.BigDecimal;
import java.util.List;

public interface OpenWeatherPort {

	List<Location> getLocation(String city);

	CurrentWeather getCurrentWeather(BigDecimal longitude, BigDecimal latitude);

}
