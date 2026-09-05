package com.maison.mabs.sotd.application.service;

import com.maison.mabs.sotd.application.port.out.AnthropicRecommendationPort;
import com.maison.mabs.sotd.application.port.in.SotdPort;
import com.maison.mabs.sotd.application.port.out.OpenWeatherPort;
import com.maison.mabs.sotd.application.port.out.UserStoragePort;
import com.maison.mabs.sotd.domain.model.anthropic.Recommendation;
import com.maison.mabs.sotd.infrastructure.adapter.in.web.exception.SotdException;
import com.maison.mabs.sotd.infrastructure.adapter.out.client.openweather.mapper.OpenWeatherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class FragranceServiceImpl implements SotdPort {

	private final AnthropicRecommendationPort anthropicRecommendationPort;

	private final OpenWeatherMapper openWeatherMapper;

	private final OpenWeatherPort openWeatherPort;

	private final UserStoragePort userStoragePort;

	@Override
	public Recommendation recommendation(UUID id) {
		var user = this.userStoragePort.findUserById(id).orElseThrow(() -> new SotdException("User not found"));

		// Todo: Cache weather by location.city
		var currentWeather = this.openWeatherPort.getCurrentWeather(user.location().longitude(),
				user.location().latitude());

		// Todo: Cache anthropic response
		var userLocation = this.openWeatherMapper.mapCurrentWeatherResponse(user.location(), currentWeather);

		var updatedUser = this.userStoragePort.updateLocation(id, userLocation);

		return this.anthropicRecommendationPort.recommend(updatedUser);
	}

}
