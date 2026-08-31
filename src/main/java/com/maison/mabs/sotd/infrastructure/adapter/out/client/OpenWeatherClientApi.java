package com.maison.mabs.sotd.infrastructure.adapter.out.client;

import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.CurrentWeather;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Location;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.math.BigDecimal;
import java.util.List;

@HttpExchange
public interface OpenWeatherClientApi {

	@GetExchange("/geo/1.0/direct")
	List<Location> getCodeCity(@RequestParam String q, @RequestParam int limit, @RequestParam String appid);

	@GetExchange("/data/2.5/weather")
	CurrentWeather getCurrentWeather(@RequestParam BigDecimal lat, @RequestParam BigDecimal lon,
			@RequestParam String appid, @RequestParam String units);

}
