package com.maison.mabs.userservice.application.ports.out;

import com.maison.mabs.userservice.domain.model.FragranceCollection;
import com.maison.mabs.userservice.domain.model.User;
import com.maison.mabs.userservice.domain.model.UserLocation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPersistencePort {

    User save(User user);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(UUID id);

    User updateCollection(UUID id, List<FragranceCollection> fragranceCollections);

    User updateLocation(UUID id, UserLocation userLocation);
}
