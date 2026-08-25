package com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request;

import com.maison.mabs.sotd.domain.model.Concentration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FragranceCollection(
//@formatter:off
		@NotEmpty(message = "Fragrance brand is required")
		@Schema(description = "Fragrance brand", example = "Amouage")
		String brand,

		@NotEmpty(message = "Fragrance name is required")
		@Schema(description = "Fragrance name", example = "Decision")
		String name,

		@NotNull(message = "Fragrance concentration is required")
		@Schema(description = "Fragrance concentration", allowableValues = {"EDT", "EDP", "EXTRAIT" })
		Concentration concentration) {
//@formatter:on

}
