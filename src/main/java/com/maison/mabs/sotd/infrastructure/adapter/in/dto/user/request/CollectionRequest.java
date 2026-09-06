package com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record CollectionRequest(@Schema(description = "Add collection request") List<FragranceCollection> collection) {
}
