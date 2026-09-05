package com.maison.mabs.sotd.infrastructure.adapter.in.web;

import com.maison.mabs.sotd.application.port.in.SotdPort;
import com.maison.mabs.sotd.domain.model.anthropic.Recommendation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Fragrance Controller", description = "Fragrance recommendation  endpoints")
public class FragranceController {

	private final SotdPort sotdPort;

	@PostMapping("/recommendation/{id}")
	public ResponseEntity<Recommendation> sotdRecommendation(@PathVariable UUID id) {
		return new ResponseEntity<>(this.sotdPort.recommendation(id), HttpStatus.OK);
	}

}
