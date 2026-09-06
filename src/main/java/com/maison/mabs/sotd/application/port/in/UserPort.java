package com.maison.mabs.sotd.application.port.in;

import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CollectionRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CreateUserRequest;

import java.util.UUID;

public interface UserPort {

	User createUserProfile(CreateUserRequest createUserRequest);

	User addCollection(UUID id, CollectionRequest collectionRequest);

}
