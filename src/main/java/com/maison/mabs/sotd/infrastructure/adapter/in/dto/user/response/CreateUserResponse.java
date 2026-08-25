package com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.response;

import com.maison.mabs.sotd.domain.model.ProfileStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateUserResponse(

		@Schema(description = "Unique identifier of users") UUID id,

		@Schema(description = "User first name", example = "John") String firstName,

		@Schema(description = "User last name") String lastName,

		@Schema(description = "User email address", example = "john.doe@gmail.com") String email,

		@Schema(description = "The city in which they reside", example = "Johannesburg") String city,

		@Schema(description = "Profile status", example = "ACTIVE") ProfileStatus status) {
}
