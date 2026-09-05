package com.maison.mabs.sotd.infrastructure.adapter.out.client.anthropic.mapper;

import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.FragranceCollection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AnthropicMapper {

	public String mapWeatherPrompt(User user) {
		var weatherDescription = "current weather at location:%s, minimum temperature:%s maximum temperature:%s";
		var userLocation = user.location();
		return String.format(weatherDescription, userLocation.currentTemperature(), userLocation.minimumTemperature(),
				userLocation.maximumTemperature());
	}

	public String formatCollection(List<FragranceCollection> collections) {
		return collections.stream()
			.map(fragrance -> "%s %s (%s)".formatted(fragrance.brand(), fragrance.brand(), fragrance.concentration()))
			.collect(Collectors.joining(", "));
	}

}
