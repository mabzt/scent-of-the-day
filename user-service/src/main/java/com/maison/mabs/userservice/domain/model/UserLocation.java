package com.maison.mabs.userservice.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record UserLocation(String city, String country, String province, @JsonIgnore BigDecimal latitude,
		@JsonIgnore BigDecimal longitude, @JsonIgnore BigDecimal currentTemperature,
		@JsonIgnore BigDecimal minimumTemperature, @JsonIgnore BigDecimal maximumTemperature) {
}
