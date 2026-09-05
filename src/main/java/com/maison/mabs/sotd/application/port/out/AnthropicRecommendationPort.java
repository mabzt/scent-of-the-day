package com.maison.mabs.sotd.application.port.out;

import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.domain.model.anthropic.Recommendation;

public interface AnthropicRecommendationPort {

	Recommendation recommend(User user);

}
