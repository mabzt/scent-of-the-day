package com.maison.mabs.sotd.application.service;

import com.maison.mabs.sotd.application.port.in.UserPort;
import com.maison.mabs.sotd.application.port.out.OpenWeatherPort;
import com.maison.mabs.sotd.application.port.out.UserStoragePort;
import com.maison.mabs.sotd.domain.model.Fragrances;
import com.maison.mabs.sotd.domain.model.ProfileStatus;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CollectionRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CreateUserRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.web.exception.SotdException;
import com.maison.mabs.sotd.infrastructure.adapter.out.client.openweather.mapper.OpenWeatherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

	private final OpenWeatherMapper openWeatherMapper;

	private final OpenWeatherPort openWeatherPort;

	private final UserStoragePort userStoragePort;

	@Override
	public User createUserProfile(CreateUserRequest createUserRequest) {
		if (this.userStoragePort.findUserByEmail(createUserRequest.email()).isPresent()) {
			log.warn("User with email: {} already exists", createUserRequest.email());
			throw new SotdException("User with email already exists");
		}

		var location = this.openWeatherPort.getLocation(createUserRequest.city());
		var userLocation = this.openWeatherMapper.mapGetLocationResponse(location)
			.orElseThrow(() -> new SotdException("Failed to geo code city"));

		Fragrances fragrances = Fragrances.builder().categories(createUserRequest.fragranceTypes()).build();

		var user = User.builder()
			.firstName(createUserRequest.firstName())
			.lastName(createUserRequest.lastName())
			.email(createUserRequest.email())
			.status(ProfileStatus.INCOMPLETE)
			.location(userLocation)
			.fragrances(fragrances)
			.build();

		return this.userStoragePort.save(user);

	}

	@Override
	public User addCollection(UUID id, CollectionRequest collectionRequest) {
		var user = this.userStoragePort.findUserById(id).orElseThrow(() -> new SotdException("User not found"));

		if (Objects.isNull(collectionRequest.collection())) {
			return user;
		}

		var mergedCollection = Objects.nonNull(collectionRequest.collection())
				? mergeDistinct(user.fragrances().collection(), collectionRequest.collection())
				: user.fragrances().collection();

		return this.userStoragePort.updateCollection(id, mergedCollection);

	}

	private static <T> List<T> mergeDistinct(List<T> existing, List<T> incoming) {
		return Stream.concat(Optional.ofNullable(existing).orElseGet(List::of).stream(), incoming.stream())
			.distinct()
			.toList();
	}

}
