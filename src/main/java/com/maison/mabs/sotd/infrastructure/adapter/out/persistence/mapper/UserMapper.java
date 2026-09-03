package com.maison.mabs.sotd.infrastructure.adapter.out.persistence.mapper;

import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.FragranceCollection;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.entity.FragranceCollectionEntity;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	@Mapping(target = "fragrances.categories", source = "fragranceTypes")
	@Mapping(target = "fragrances.collection", source = "fragranceCollection")
	User toDomain(UsersEntity usersEntity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "updatedOn", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "fragranceTypes", source = "fragrances.categories")
	@Mapping(target = "fragranceCollection", source = "fragrances.collection")
	UsersEntity toEntity(User user);

	List<FragranceCollectionEntity> toFragranceCollectionEntities(List<FragranceCollection> collections);

}
