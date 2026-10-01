package com.maison.mabs.userservice.infrastructure.adapter.out.mapper;

import com.maison.mabs.userservice.domain.model.FragranceCollection;
import com.maison.mabs.userservice.domain.model.User;
import com.maison.mabs.userservice.domain.model.UserLocation;
import com.maison.mabs.userservice.infrastructure.adapter.out.persistence.entity.FragranceCollectionJpaEntity;
import com.maison.mabs.userservice.infrastructure.adapter.out.persistence.entity.Location;
import com.maison.mabs.userservice.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserJpaEntity userJpaEntity);

    UserJpaEntity toEntity(User user);

    Location toLocationEntity(UserLocation userLocation);

    List<FragranceCollectionJpaEntity> toFragranceCollectionEntities(List<FragranceCollection> collections);
}
