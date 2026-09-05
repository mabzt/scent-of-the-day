package com.maison.mabs.sotd.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record UserLocation(String city, String country, String province, @JsonIgnore BigDecimal latitude,
		@JsonIgnore BigDecimal longitude, @JsonIgnore BigDecimal currentTemperature,
		@JsonIgnore BigDecimal minimumTemperature, @JsonIgnore BigDecimal maximumTemperature) {
}
