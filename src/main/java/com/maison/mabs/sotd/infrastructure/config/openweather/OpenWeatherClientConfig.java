package com.maison.mabs.sotd.infrastructure.config.openweather;

import com.maison.mabs.sotd.infrastructure.adapter.out.client.openweather.OpenWeatherClientApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OpenWeatherConfigProperties.class)
public class OpenWeatherClientConfig {

	private final OpenWeatherConfigProperties configProperties;

	@Bean
	public OpenWeatherClientApi openWeatherClientApi(RestClient.Builder builder) {
		RestClient restClient = builder.baseUrl(this.configProperties.baseUrl())
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();

		return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
			.build()
			.createClient(OpenWeatherClientApi.class);

	}

}
