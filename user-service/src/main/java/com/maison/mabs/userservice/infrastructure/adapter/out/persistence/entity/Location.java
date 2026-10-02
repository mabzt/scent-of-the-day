package com.maison.mabs.userservice.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Location {

	private String city;

	private String country;

	private String province;

}
