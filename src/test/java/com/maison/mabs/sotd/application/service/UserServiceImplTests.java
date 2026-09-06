package com.maison.mabs.sotd.application.service;

import com.maison.mabs.sotd.application.port.out.OpenWeatherPort;
import com.maison.mabs.sotd.application.port.out.UserStoragePort;
import com.maison.mabs.sotd.domain.model.ProfileStatus;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.FragranceCollection;
import com.maison.mabs.sotd.infrastructure.adapter.in.web.exception.SotdException;
import com.maison.mabs.sotd.infrastructure.adapter.out.client.openweather.mapper.OpenWeatherMapper;
import com.maison.mabs.sotd.utils.UserTestDataUtil;
import com.maison.mabs.sotd.utils.LocationTestDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

	@Mock
	private OpenWeatherMapper openWeatherMapper;

	@Mock
	private OpenWeatherPort openWeatherPort;

	@Mock
	private UserStoragePort userStoragePort;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void shouldCreateProfileWhenRequestIsValid() {
		// Given
		var createUserRequest = UserTestDataUtil.validRequest();
		var locations = List.of(LocationTestDataUtil.validLocation());

		// When
		Mockito.when(this.userStoragePort.findUserByEmail(createUserRequest.email())).thenReturn(Optional.empty());
		Mockito.when(this.openWeatherPort.getLocation(createUserRequest.city())).thenReturn(locations);
		Mockito.when(this.openWeatherMapper.mapGetLocationResponse(locations))
			.thenReturn(Optional.of(UserTestDataUtil.validUserLocation()));

		// Then
		this.userService.createUserProfile(createUserRequest);

		var userCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(this.userStoragePort).save(userCaptor.capture());
		Assertions.assertEquals(ProfileStatus.INCOMPLETE, userCaptor.getValue().status());

	}

	@Test
	void shouldThrowExceptionWhenUserEmailExists() {
		// Given
		var createUserRequest = UserTestDataUtil.validRequest();

		// When
		Mockito.when(this.userStoragePort.findUserByEmail(createUserRequest.email()))
			.thenReturn(Optional.of(UserTestDataUtil.validUser()));

		// Then
		org.assertj.core.api.Assertions.assertThatThrownBy(() -> this.userService.createUserProfile(createUserRequest))
			.isInstanceOf(SotdException.class)
			.hasMessage("User with email already exists");
	}

	@Test
	void shouldThrowExceptionWhenGeoCodingReturnsNoMatch() {
		// Given
		var createUserRequest = UserTestDataUtil.validRequest();

		// When
		Mockito.when(this.userStoragePort.findUserByEmail(createUserRequest.email())).thenReturn(Optional.empty());

		// Then
		org.assertj.core.api.Assertions.assertThatThrownBy(() -> this.userService.createUserProfile(createUserRequest))
			.isInstanceOf(SotdException.class)
			.hasMessage("Failed to geo code city");

	}

	@Test
	void shouldUpdateCollectionWhenRequestIsValid() {
		// Given
		var addCollectionRequest = UserTestDataUtil.collectionRequest();
		var user = UserTestDataUtil.validUser();

		@SuppressWarnings("unchecked")
		ArgumentCaptor<List<FragranceCollection>> collectionCaptor = ArgumentCaptor.forClass(List.class);

		Mockito.when(this.userStoragePort.findUserById(user.id())).thenReturn(Optional.of(user));
		Mockito.when(this.userStoragePort.updateCollection(Mockito.any(), Mockito.any())).thenReturn(user);

		// When
		this.userService.addCollection(user.id(), addCollectionRequest);

		// Then
		Mockito.verify(this.userStoragePort).updateCollection(Mockito.eq(user.id()), collectionCaptor.capture());

		var expectedMerged = Stream
			.concat(user.fragrances().collection().stream(), addCollectionRequest.collection().stream())
			.distinct()
			.toList();

		org.assertj.core.api.Assertions.assertThat(collectionCaptor.getValue())
			.containsExactlyInAnyOrderElementsOf(expectedMerged);
	}

	@Test
	void shouldNotUpdateCollectionWhenAddCollectionIsNull() {
		// Given
		var user = UserTestDataUtil.validUser();
		var numberOfFragrances = user.fragrances().collection().size();
		var collectionRequest = UserTestDataUtil.collectionRequest();
		collectionRequest.toBuilder().collection(null).build();

		Mockito.when(this.userStoragePort.findUserById(user.id())).thenReturn(Optional.of(user));

		// When
		this.userService.addCollection(user.id(), collectionRequest);

		// Then
		Assertions.assertEquals(numberOfFragrances, user.fragrances().collection().size());

	}

}
