package com.maison.mabs.sotd.infrastructure.adapter.in.web;

import com.maison.mabs.sotd.application.port.in.SotdPort;
import com.maison.mabs.sotd.domain.model.anthropic.Recommendation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@WebMvcTest(FragranceController.class)
public class FragranceControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private SotdPort sotdPort;

	@Test
	void shouldReturnRecommendationWhenUserExists() throws Exception {
		var id = UUID.randomUUID();
		var recommendation = Recommendation.builder()
			.scentOfTheDay("Amouage Search - a bold, resinous pick suited to today's cool weather.")
			.build();

		Mockito.when(this.sotdPort.recommendation(id)).thenReturn(recommendation);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/recommendation/{id}", id).header("Api-Version", "1.0"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.scentOfTheDay").value(recommendation.scentOfTheDay()));

		Mockito.verify(this.sotdPort).recommendation(id);
	}

	@Test
	void shouldReturnBadRequestWhenApiVersionIsNotProvided() throws Exception {
		var id = UUID.randomUUID();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/recommendation/{id}", id))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("API version is required."));

		Mockito.verifyNoInteractions(this.sotdPort);

	}

}
