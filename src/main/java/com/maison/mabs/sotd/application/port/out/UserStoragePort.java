package com.maison.mabs.sotd.application.port.out;

import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.domain.model.UserLocation;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.FragranceCollection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStoragePort {

	User save(User user);

	Optional<User> findUserByEmail(String email);

	Optional<User> findUserById(UUID id);

	User updateCollection(UUID id, List<FragranceCollection> fragranceCollections);

	User updateLocation(UUID id, UserLocation userLocation);

}
