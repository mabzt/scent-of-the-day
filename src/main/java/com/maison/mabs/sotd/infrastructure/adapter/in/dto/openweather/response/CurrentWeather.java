package com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentWeather(

		@JsonProperty("weather") List<Weather> weather,

		@JsonProperty("main") WeatherMain main,

		@JsonProperty("wind") Wind wind,

		@JsonProperty("clouds") Clouds clouds,

		@JsonProperty("dt") Instant observedAt

) {
}
