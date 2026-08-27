package com.maison.mabs.sotd.infrastructure.adapter.out.persistence;

import com.maison.mabs.sotd.application.port.out.UserJpaPort;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdaptor implements UserJpaPort {

	private final UserRepository userRepository;

	@Override
	public User save(User user) {
		return null;
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		return Optional.empty();
	}

	@Override
	public Optional<User> findUserById(UUID id) {
		return Optional.empty();
	}

}
