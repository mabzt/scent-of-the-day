package com.maison.mabs.sotd.infrastructure.adapter.out.persistence.mapper;

import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	User toDomain(UsersEntity usersEntity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "updatedOn", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	UsersEntity toEntity(User user);

}
