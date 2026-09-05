package com.maison.mabs.sotd.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Embeddable
public class Location {

	private String city;

	private String country;

	private String province;

	@Column(name = "latitude", precision = 9, scale = 6)
	private BigDecimal latitude;

	@Column(name = "longitude", precision = 9, scale = 6)
	private BigDecimal longitude;

	private BigDecimal currentTemperature;

	private BigDecimal minimumTemperature;

	private BigDecimal maximumTemperature;

}
