package com.maison.mabs.sotd.utils;

import com.maison.mabs.sotd.infrastructure.adapter.in.dto.openweather.response.Location;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class LocationTestDataUtil {

	public Location validLocation() {
		return Location.builder()
			.city("Midrand")
			.latitude(BigDecimal.valueOf(-25.999262))
			.longitude(BigDecimal.valueOf(28.125912))
			.country("ZA")
			.province("Gauteng")
			.build();
	}

}
