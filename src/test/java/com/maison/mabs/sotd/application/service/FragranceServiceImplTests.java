package com.maison.mabs.sotd.application.service;

import com.maison.mabs.sotd.application.port.out.AnthropicRecommendationPort;
import com.maison.mabs.sotd.application.port.out.OpenWeatherPort;
import com.maison.mabs.sotd.application.port.out.UserStoragePort;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.web.exception.SotdException;
import com.maison.mabs.sotd.infrastructure.adapter.out.client.openweather.mapper.OpenWeatherMapper;
import com.maison.mabs.sotd.utils.LocationTestDataUtil;
import com.maison.mabs.sotd.utils.OpenWeatherTestDataUtil;
import com.maison.mabs.sotd.utils.UserTestDataUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class FragranceServiceImplTests {

	@Mock
	private AnthropicRecommendationPort anthropicRecommendationPort;

	@Mock
	private OpenWeatherMapper openWeatherMapper;

	@Mock
	private OpenWeatherPort openWeatherPort;

	@Mock
	private UserStoragePort userStoragePort;

	@InjectMocks
	private FragranceServiceImpl fragranceService;

	@Test
	void shouldReturnRecommendationWhenRequestIsValid() {
		// Given
		var user = UserTestDataUtil.validUser();
		var currentWeather = OpenWeatherTestDataUtil.validWeather();
		var userLocation = LocationTestDataUtil.validUserLocation();
		var updatedUser = user.toBuilder().location(userLocation).build();

		Mockito.when(this.userStoragePort.findUserById(Mockito.any(UUID.class))).thenReturn(Optional.of(user));
		Mockito.when(this.openWeatherPort.getCurrentWeather(user.location().longitude(), user.location().latitude()))
			.thenReturn(currentWeather);
		Mockito.when(this.openWeatherMapper.mapCurrentWeatherResponse(user.location(), currentWeather))
			.thenReturn(userLocation);
		Mockito.when(this.userStoragePort.updateLocation(user.id(), userLocation)).thenReturn(updatedUser);

		// When
		this.fragranceService.recommendation(user.id());

		// Then
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(this.anthropicRecommendationPort).recommend(userArgumentCaptor.capture());

		Assertions.assertThat(userArgumentCaptor.getValue()).isEqualTo(updatedUser);
	}

	@Test
	void shouldThrowExceptionWhenUserIsNotFound() {
		// Given
		var userId = UUID.randomUUID();

		// When
		Mockito.when(this.userStoragePort.findUserById(userId)).thenReturn(Optional.empty());

		// Then
		Assertions.assertThatThrownBy(() -> this.fragranceService.recommendation(userId))
			.isInstanceOf(SotdException.class)
			.hasMessage("User not found");

		Mockito.verifyNoInteractions(this.openWeatherPort, this.openWeatherMapper, this.anthropicRecommendationPort);

	}

}
