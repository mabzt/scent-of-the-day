package com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request;

import com.maison.mabs.sotd.domain.model.FragranceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateUserRequest(
//@formatter:off
		@NotEmpty(message = "First name is required") @Schema(description = "User first name",
				example = "John") String firstName,

		@NotEmpty(message = "Last name is required") @Schema(description = "User last name") String lastName,

		@Email(message = "Invalid email address") @NotEmpty(message = "Email address is required") @Schema(
				description = "User email address", example = "john.doe@gmail.com") String email,

		@NotEmpty(message = "City is required") @Schema(description = "The city in which they reside",
				example = "Johannesburg") String city,

		@NotNull(message = "Fragrance type cannot be null") @Schema(description = "Fragrance types", allowableValues = {
				"NICHE", "DESIGNER", "DUPE" }) List<FragranceType> fragranceTypes) {
	//@formatter:on
}
