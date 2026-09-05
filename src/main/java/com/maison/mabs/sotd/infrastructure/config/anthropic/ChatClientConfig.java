package com.maison.mabs.sotd.infrastructure.config.anthropic;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

	@Bean
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
		return chatClientBuilder.defaultSystem("You are a fragrance recommendation assistant for Scent of the Day.")
			.build();
	}

}
