package com.maison.mabs.sotd.application.port.in;

import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CollectionRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CreateUserRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface UserPort {

	// Create Profile
	User createUserProfile(@Valid @NotNull CreateUserRequest createUserRequest);

	User addCollection(@NotNull UUID id, @Valid @NotNull CollectionRequest collectionRequest);

}
