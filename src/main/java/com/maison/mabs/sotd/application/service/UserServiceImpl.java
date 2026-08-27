package com.maison.mabs.sotd.application.service;

import com.maison.mabs.sotd.application.port.in.UserPort;
import com.maison.mabs.sotd.application.port.out.UserJpaPort;
import com.maison.mabs.sotd.domain.model.ProfileStatus;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CollectionRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CreateUserRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.exception.SotdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements UserPort {

	private final UserJpaPort userJpaPort;

	@Override
	@Transactional
	public User createUserProfile(CreateUserRequest createUserRequest) {
		if (this.userJpaPort.findUserByEmail(createUserRequest.email()).isPresent()) {
			log.warn("User with email: {} already exists", createUserRequest.email());
			throw new SotdException("User with email already exists");
		}

		var user = User.builder()
			.firstName(createUserRequest.firstName())
			.lastName(createUserRequest.lastName())
			.email(createUserRequest.email())
			.city(createUserRequest.city())
			.country(createUserRequest.country())
			.status(ProfileStatus.INCOMPLETE)
			.build();

		return this.userJpaPort.save(user);
	}

	@Override
	@Transactional
	public User addCollection(UUID id, CollectionRequest collectionRequest) {
		// Todo: Relook this. Is updating of preferred fragrance types better suited as
		// seperate service of will this
		// suffice using a patch and only sending the data you need?
		var user = this.userJpaPort.findUserById(id).orElseThrow(() -> new SotdException("User not found"));

		var userBuilder = user.toBuilder();
		var updated = false;

		if (Objects.nonNull(collectionRequest.collection())) {
			userBuilder
				.fragranceCollections(mergeDistinct(user.fragranceCollections(), collectionRequest.collection()));
			updated = true;
		}

		if (Objects.nonNull(collectionRequest.fragranceTypes())) {
			userBuilder.fragranceTypes(mergeDistinct(user.fragranceTypes(), collectionRequest.fragranceTypes()));
			updated = true;
		}

		return updated ? this.userJpaPort.save(userBuilder.build()) : user;

	}

	private static <T> List<T> mergeDistinct(List<T> existing, List<T> incoming) {
		return Stream.concat(Optional.ofNullable(existing).orElseGet(List::of).stream(), incoming.stream())
			.distinct()
			.toList();
	}

}
