package com.maison.mabs.sotd.infrastructure.adapter.out.client.openweather.mapper;

import com.maison.mabs.sotd.domain.model.UserLocation;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class OpenWeatherMapper {

	public Optional<UserLocation> mapGetLocationResponse(List<Location> locations) {
		if (locations == null || locations.isEmpty()) {
			return Optional.empty();
		}

		if (locations.size() > 1) {
			log.debug("Geocoding returned {} candidate locations, using the first: {}", locations.size(),
					locations.getFirst().city());
		}

		return Optional.of(mapUserLocation(locations.getFirst()));

	}

	private UserLocation mapUserLocation(Location location) {
		return UserLocation.builder()
			.city(location.city())
			.latitude(location.latitude())
			.longitude(location.longitude())
			.province(location.province())
			.country(location.country())
			.build();
	}

}
