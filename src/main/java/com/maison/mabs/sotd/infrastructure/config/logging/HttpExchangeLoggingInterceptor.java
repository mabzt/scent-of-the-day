package com.maison.mabs.sotd.infrastructure.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class HttpExchangeLoggingInterceptor implements ClientHttpRequestInterceptor {

	private static final String LOG_REQUEST = "\nEXCHANGE ID: {} \nTIMESTAMP: {} \nMETHOD: {} \nURI: {} \nHEADERS: {} \nREQUEST: {}";

	private static final String LOG_RESPONSE = "\nEXCHANGE ID:{} \nTIMESTAMP: {} \nHEADERS:{} \nSTATUS CODE:{} \nDuration:{} \nRESPONSE:{}";

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Override
	public @NonNull ClientHttpResponse intercept(@NonNull HttpRequest request, byte @NonNull [] body,
			ClientHttpRequestExecution execution) throws IOException {

		String exchangeId = UUID.randomUUID().toString();
		logRequest(exchangeId, request, body);

		long startTime = System.nanoTime();
		ClientHttpResponse response = execution.execute(request, body);
		long duration = (System.nanoTime() - startTime) / 1_000_000;

		logResponse(exchangeId, response, duration);
		return response;
	}

	private void logRequest(String id, HttpRequest request, byte[] body) {
		log.debug(LOG_REQUEST, id, LocalDateTime.now(), request.getMethod(), request.getURI(), request.getHeaders(),
				formatBody(body));
	}

	private void logResponse(String id, ClientHttpResponse response, long duration) throws IOException {
		String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
		log.debug(LOG_RESPONSE, id, LocalDateTime.now(), response.getHeaders(), response.getStatusCode(), duration,
				formatBody(body.getBytes(StandardCharsets.UTF_8))); // This is dodgy! look
																	// into AOP to
																	// standarise and
																	// improve logging

	}

	private String formatBody(byte[] body) {
		// This is dodgy! look into AOP to standarise and improve logging
		if (body != null && body.length > 0) {
			String toJson = new String(body, StandardCharsets.UTF_8);
			Object json = OBJECT_MAPPER.readValue(toJson, Object.class);
			return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		}
		return null;
	}

	// Todo: Mask sensitive information in headers.

}
