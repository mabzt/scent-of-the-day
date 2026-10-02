package com.maison.mabs.userservice.domain.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserLocation(String city, String country, String province) {
}
