package com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Weather(String main, String description) {
}
