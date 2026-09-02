package com.maison.mabs.sotd.domain.model;

import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.FragranceCollection;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record User(UUID id, String firstName, String lastName, String email, ProfileStatus status,
		UserLocation location, List<FragranceType> fragranceTypes, List<FragranceCollection> fragranceCollections) {
}
