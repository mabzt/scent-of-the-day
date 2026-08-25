package com.maison.mabs.sotd.application.port.in;

import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CollectionRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CreateUserRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.response.CreateUserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UserPort {

	// Create Profile
	CreateUserResponse createUserProfile(@Valid @NotNull CreateUserRequest createUserRequest);

	User addCollection(@Valid @NotNull CollectionRequest collectionRequest);

}
