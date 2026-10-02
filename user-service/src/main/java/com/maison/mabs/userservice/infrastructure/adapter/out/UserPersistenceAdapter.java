package com.maison.mabs.userservice.infrastructure.adapter.out;

import com.maison.mabs.userservice.application.ports.out.UserPersistencePort;
import com.maison.mabs.userservice.domain.model.FragranceCollection;
import com.maison.mabs.userservice.domain.model.User;
import com.maison.mabs.userservice.domain.model.UserLocation;
import com.maison.mabs.userservice.infrastructure.adapter.in.web.exception.ConflictException;
import com.maison.mabs.userservice.infrastructure.adapter.out.exception.UserException;
import com.maison.mabs.userservice.infrastructure.adapter.out.mapper.UserMapper;
import com.maison.mabs.userservice.infrastructure.adapter.out.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

	private final UserJpaRepository userRepository;

	private final UserMapper userMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public User save(User user) {
		try {
			var userEntity = this.userMapper.toEntity(user);
			var savedEntity = this.userRepository.save(userEntity);
			return this.userMapper.toDomain(savedEntity);
		}
		catch (DataIntegrityViolationException exception) {
			// Handle race conditions where two requests for the same user arrive at the
			// same time bypassing the findUserByEmail check
			throw new UserException("User with email already exists");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findUserByEmail(String email) {
		return this.userRepository.findByEmail(email).map(this.userMapper::toDomain);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findUserById(UUID id) {
		return this.userRepository.findById(id).map(this.userMapper::toDomain);
	}

	@Override
	@Transactional
	public User updateCollection(UUID id, List<FragranceCollection> fragranceCollections) {
		var userEntity = this.userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));

		// clear collection
		userEntity.getFragranceCollection().clear();

		var collectionEntities = this.userMapper.toFragranceCollectionEntities(fragranceCollections);
		collectionEntities.forEach(c -> c.setUser(userEntity));
		userEntity.getFragranceCollection().addAll(collectionEntities);

		try {
			this.userRepository.flush();
		}
		catch (ObjectOptimisticLockingFailureException exception) {
			log.warn("Concurrent modification detected for user : {}", id);
			throw new ConflictException("User collection was updated concurrently");
		}

		return this.userMapper.toDomain(userEntity);

	}

	@Override
	public User updateLocation(UUID id, UserLocation userLocation) {
		var userEntity = this.userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
		userEntity.setLocation(this.userMapper.toLocationEntity(userLocation));
		return this.userMapper.toDomain(userEntity);
	}

}
