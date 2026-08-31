package com.maison.mabs.sotd.infrastructure.config.openweather;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "openweather")
public record OpenWeatherConfigProperties(
        @NotNull boolean enabled,
        @NotEmpty String apiKey,
        @NotEmpty String baseUrl
) {
}
