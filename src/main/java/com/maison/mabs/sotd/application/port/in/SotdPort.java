package com.maison.mabs.sotd.application.port.in;

import com.maison.mabs.sotd.domain.model.anthropic.Recommendation;

import java.util.UUID;

public interface SotdPort {

	Recommendation recommendation(UUID id);

}
