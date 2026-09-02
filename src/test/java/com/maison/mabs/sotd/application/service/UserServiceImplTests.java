package com.maison.mabs.sotd.application.service;

import com.maison.mabs.sotd.application.port.out.OpenWeatherPort;
import com.maison.mabs.sotd.application.port.out.UserJpaPort;
import com.maison.mabs.sotd.domain.model.ProfileStatus;
import com.maison.mabs.sotd.domain.model.User;
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

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

	@Mock
	private OpenWeatherMapper openWeatherMapper;

	@Mock
	private OpenWeatherPort openWeatherPort;

	@Mock
	private UserJpaPort userJpaPort;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void shouldCreateProfileWhenRequestIsValid() {
		// Given
		var createUserRequest = UserTestDataUtil.validRequest();
		var locations = List.of(LocationTestDataUtil.validLocation());

		Mockito.when(this.userJpaPort.findUserByEmail(createUserRequest.email())).thenReturn(Optional.empty());
		Mockito.when(this.openWeatherPort.getLocation(createUserRequest.city())).thenReturn(locations);
		Mockito.when(this.openWeatherMapper.mapGetLocationResponse(locations))
			.thenReturn(Optional.of(UserTestDataUtil.validUserLocation()));

		// When
		this.userService.createUserProfile(createUserRequest);

		// Then
		var userCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(this.userJpaPort).save(userCaptor.capture());
		Assertions.assertEquals(ProfileStatus.INCOMPLETE, userCaptor.getValue().status());

	}

	@Test
	void shouldThrowExceptionWhenUserEmailExists() {
		// Given
		var createUserRequest = UserTestDataUtil.validRequest();

		// When
		Mockito.when(this.userJpaPort.findUserByEmail(createUserRequest.email()))
			.thenReturn(Optional.of(UserTestDataUtil.validUser()));

		// Then
		var exception = Assertions.assertThrows(SotdException.class,
				() -> this.userService.createUserProfile(createUserRequest));

		Assertions.assertEquals("User with email already exists", exception.getMessage());

	}

	@Test
	void shouldThrowExceptionWhenGeoCodingReturnsNoMatch() {
		// Given
		var createUserRequest = UserTestDataUtil.validRequest();

		// When
		Mockito.when(this.userJpaPort.findUserByEmail(createUserRequest.email())).thenReturn(Optional.empty());

		// Then
		var exception = Assertions.assertThrows(SotdException.class,
				() -> this.userService.createUserProfile(createUserRequest));

		Assertions.assertEquals("Failed to geo code city", exception.getMessage());

	}

}
