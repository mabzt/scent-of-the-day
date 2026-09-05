package com.maison.mabs.sotd.infrastructure.adapter.out.client.anthropic;

import com.maison.mabs.sotd.application.port.out.AnthropicRecommendationPort;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.domain.model.anthropic.Recommendation;
import com.maison.mabs.sotd.infrastructure.adapter.out.client.anthropic.mapper.AnthropicMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnthropicRecommendationAdapter implements AnthropicRecommendationPort {

	private final AnthropicMapper anthropicMapper;

	private final ChatClient chatClient;

	@Value("classpath:prompts/sotd_recommendation_prompt.st")
	private Resource promptResource;

	@Override
	public Recommendation recommend(User user) {
		var promptTemplate = new PromptTemplate(this.promptResource);
		// Todo move to anthropicMapper
		var prompt = promptTemplate
			.create(Map.of("collection", this.anthropicMapper.formatCollection(user.fragrances().collection()),
					"weather", this.anthropicMapper.mapWeatherAndLocation(user), "longitude",
					user.location().longitude(), "latitude", user.location().latitude()));

		log.info("SOTD prompt \n{} ", prompt.getContents());
		var response = this.chatClient.prompt(prompt).call().content();
		return Recommendation.builder().scentOfTheDay(response).build();
	}

}
