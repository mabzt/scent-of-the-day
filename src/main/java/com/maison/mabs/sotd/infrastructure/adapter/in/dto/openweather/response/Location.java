package com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Location(@JsonProperty("name") String city,

		@JsonProperty("lat") BigDecimal latitude, @JsonProperty("lon") BigDecimal longitude, String country,
		@JsonProperty("state") String province) {
}
