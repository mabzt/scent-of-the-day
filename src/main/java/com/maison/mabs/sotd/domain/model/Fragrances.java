package com.maison.mabs.sotd.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.FragranceCollection;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Fragrances(List<FragranceType> categories, List<FragranceCollection> collection) {
}
