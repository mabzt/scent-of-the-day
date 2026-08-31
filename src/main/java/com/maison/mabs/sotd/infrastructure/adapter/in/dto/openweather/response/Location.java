package com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Location(String name, BigDecimal latitude, BigDecimal longitude, String country, String state) {
}
