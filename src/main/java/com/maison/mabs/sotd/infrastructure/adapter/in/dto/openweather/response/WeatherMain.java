package com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherMain(

		@JsonProperty("temp") BigDecimal temperature,

		@JsonProperty("feels_like") BigDecimal feelsLike,

		@JsonProperty("temp_min") BigDecimal minimumTemperature,

		@JsonProperty("temp_max") BigDecimal maximumTemperature,

		@JsonProperty("humidity") Integer humidity) {
}
