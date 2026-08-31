package com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Wind(BigDecimal speed,

		@JsonProperty("deg") Integer direction) {
}
