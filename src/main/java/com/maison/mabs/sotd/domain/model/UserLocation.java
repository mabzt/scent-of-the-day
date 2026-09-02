package com.maison.mabs.sotd.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UserLocation(String city, String country, String province, @JsonIgnore BigDecimal latitude,
		@JsonIgnore BigDecimal longitude) {
}
