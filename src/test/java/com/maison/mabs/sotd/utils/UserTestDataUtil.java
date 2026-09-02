package com.maison.mabs.sotd.utils;

import com.maison.mabs.sotd.domain.model.Concentration;
import com.maison.mabs.sotd.domain.model.FragranceType;
import com.maison.mabs.sotd.domain.model.ProfileStatus;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.domain.model.UserLocation;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CreateUserRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.FragranceCollection;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class UserTestDataUtil {

	public User validUser() {
		return User.builder()
			.id(UUID.randomUUID())
			.firstName("Kabza")
			.lastName("De Small")
			.email("kabza@piano.com")
			.status(ProfileStatus.ACTIVE)
			.location(validUserLocation())
			.fragranceTypes(fragranceTypes())
			.fragranceCollections(validFragranceCollection())
			.build();

	}

	public CreateUserRequest validRequest() {
		return CreateUserRequest.builder()
			.firstName("John")
			.lastName("Cena")
			.email("john.cena@wwe.com")
			.city("Johannesburg")
			.fragranceTypes(fragranceTypes())
			.build();
	}

	public UserLocation validUserLocation() {
		return UserLocation.builder()
			.city("Midrand")
			.latitude(BigDecimal.valueOf(-25.999262))
			.longitude(BigDecimal.valueOf(28.125912))
			.country("ZA")
			.province("Gauteng")
			.build();
	}

	public List<FragranceType> fragranceTypes() {
		return List.of(FragranceType.DUPE, FragranceType.NICHE, FragranceType.DESIGNER);
	}

	public List<FragranceCollection> validFragranceCollection() {
		return List.of(
				FragranceCollection.builder().brand("Amouage").name("Search").concentration(Concentration.EDP).build(),
				FragranceCollection.builder().brand("Creed").name("Aventus").concentration(Concentration.EDP).build(),
				FragranceCollection.builder().brand("Dior").name("Sauvage").concentration(Concentration.EDT).build(),
				FragranceCollection.builder()
					.brand("Maison Crivelli")
					.name("Patchouli Magnetik")
					.concentration(Concentration.EXTRAIT)
					.build(),
				FragranceCollection.builder().brand("Xerjoff ").name("Naxos").concentration(Concentration.EDP).build());
	}

}
