package com.maison.mabs.sotd.infrastructure.adapter.out.persistence.entity;

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

	private BigDecimal latitude;

	private BigDecimal longitude;

}
