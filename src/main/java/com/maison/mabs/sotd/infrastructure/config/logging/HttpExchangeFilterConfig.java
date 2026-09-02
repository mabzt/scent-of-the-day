package com.maison.mabs.sotd.infrastructure.config.logging;

import org.springframework.boot.restclient.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;

@Configuration
public class HttpExchangeFilterConfig {

	@Bean
	public RestClientCustomizer restClientCustomizer() {
		return builder -> builder
			.requestFactory(new BufferingClientHttpRequestFactory(new JdkClientHttpRequestFactory()))
			.requestInterceptor(new HttpExchangeLoggingInterceptor());
	}

}
