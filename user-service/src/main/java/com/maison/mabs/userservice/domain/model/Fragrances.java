package com.maison.mabs.userservice.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Fragrances(List<FragranceType> categories, List<FragranceCollection> collection) {
}
