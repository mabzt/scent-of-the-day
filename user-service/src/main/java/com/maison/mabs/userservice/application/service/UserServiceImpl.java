package com.maison.mabs.userservice.application.service;

import com.maison.mabs.userservice.application.ports.in.CreateProfileUseCase;
import com.maison.mabs.userservice.application.ports.out.UserPersistencePort;
import com.maison.mabs.userservice.domain.model.Fragrances;
import com.maison.mabs.userservice.domain.model.ProfileStatus;
import com.maison.mabs.userservice.domain.model.User;
import com.maison.mabs.userservice.domain.model.UserLocation;
import com.maison.mabs.userservice.domain.model.dto.CreateUserRequest;
import com.maison.mabs.userservice.infrastructure.adapter.out.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements CreateProfileUseCase {

	private final UserPersistencePort userPersistencePort;

	@Override
	public User createUserProfile(CreateUserRequest createUserRequest) {
		if (this.userPersistencePort.findUserByEmail(createUserRequest.email()).isPresent()) {
			log.warn("User with email: {} already exists", createUserRequest.email());
			throw new UserException("User with email " + createUserRequest.email() + " already exists");
		}

		var fragrances = Fragrances.builder().categories(createUserRequest.fragranceTypes()).build();

		var location = UserLocation.builder()
			.city(createUserRequest.city())
			.country(createUserRequest.province())
			.province(createUserRequest.province())
			.build();

		var user = User.builder()
			.firstName(createUserRequest.firstName())
			.lastName(createUserRequest.lastName())
			.email(createUserRequest.email())
			.status(ProfileStatus.INCOMPLETE)
			.location(location)
			.fragrances(fragrances)
			.build();

		return this.userPersistencePort.save(user);

	}

}
