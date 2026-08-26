package com.maison.mabs.sotd.application.port.out;

import com.maison.mabs.sotd.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaPort {

	User save(User user);

	Optional<User> findUserByEmail(String email);

	Optional<User> findUserById(UUID id);

}
