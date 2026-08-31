package com.maison.mabs.sotd.application.port.out;

import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.CurrentWeather;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Location;
import lombok.NonNull;

import java.math.BigDecimal;

public interface OpenWeatherPort {

	Location getLocation(@NonNull String city);

	CurrentWeather getCurrentWeather(@NonNull BigDecimal longitude, @NonNull BigDecimal latitude);

}
