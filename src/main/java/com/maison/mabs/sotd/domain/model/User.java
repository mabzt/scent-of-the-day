package com.maison.mabs.sotd.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record User(UUID id, String firstName, String lastName, String email, ProfileStatus status,
		UserLocation location, Fragrances fragrances, @JsonIgnore Long version) {
}
