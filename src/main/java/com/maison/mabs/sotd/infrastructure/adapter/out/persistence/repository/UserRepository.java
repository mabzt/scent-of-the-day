package com.maison.mabs.sotd.infrastructure.adapter.out.persistence.repository;

import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UsersEntity, UUID> {

	Optional<UsersEntity> findByEmail(String email);

}
