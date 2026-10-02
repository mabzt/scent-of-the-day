package com.maison.mabs.userservice.infrastructure.adapter.out.mapper;

import com.maison.mabs.userservice.domain.model.FragranceCollection;
import com.maison.mabs.userservice.domain.model.User;
import com.maison.mabs.userservice.infrastructure.adapter.out.persistence.entity.FragranceCollectionJpaEntity;
import com.maison.mabs.userservice.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	User toDomain(UserJpaEntity userJpaEntity);

	UserJpaEntity toEntity(User user);

	List<FragranceCollectionJpaEntity> toFragranceCollectionEntities(List<FragranceCollection> collections);

}
