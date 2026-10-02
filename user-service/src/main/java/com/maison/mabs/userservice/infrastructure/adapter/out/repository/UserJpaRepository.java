package com.maison.mabs.userservice.infrastructure.adapter.out.repository;

import com.maison.mabs.userservice.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {

	Optional<UserJpaEntity> findByEmail(String email);

	Optional<UserJpaEntity> findById(UUID id);

}
