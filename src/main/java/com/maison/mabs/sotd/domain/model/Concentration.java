package com.maison.mabs.sotd.domain.model;

import lombok.Getter;

@Getter
public enum Concentration {

	/**
	 * Eau de Toilette. Oil level: 5% to 15%, Lasts up to 4 to 6 hours. Lighter, popular
	 * for casual use
	 */
	EDT("Eau de Toilette"),

	/**
	 * Eau de Parfum. Oil level: 15% to 20% (sometimes 10% to 20%). Lasts: 6 to 8 hours.
	 * Great balance of power and daily wear
	 */
	EDP("Eau de Parfum"),

	/**
	 * Extrait de Parfum (Parfum). Oil level: 20% to 40%. Lasts: 8 to 12+ hours. Highest
	 * strength and price
	 */

	EXTRAIT("Extrait de Parfum");

	private final String description;

	Concentration(String description) {
		this.description = description;
	}

}
