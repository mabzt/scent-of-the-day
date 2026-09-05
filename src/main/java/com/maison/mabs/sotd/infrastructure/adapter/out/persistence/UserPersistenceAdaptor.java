package com.maison.mabs.sotd.infrastructure.adapter.out.persistence;

import com.maison.mabs.sotd.application.port.out.UserStoragePort;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.domain.model.UserLocation;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.FragranceCollection;
import com.maison.mabs.sotd.infrastructure.adapter.in.web.exception.SotdConflictException;
import com.maison.mabs.sotd.infrastructure.adapter.in.web.exception.SotdException;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.mapper.UserMapper;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.repository.UserRepository;
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
public class UserPersistenceAdaptor implements UserStoragePort {

	private final UserRepository userRepository;

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
			throw new SotdException("User with email already exists");
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
		var userEntity = this.userRepository.findById(id).orElseThrow(() -> new SotdException("User not found"));

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
			throw new SotdConflictException("User collection was updated concurrently");
		}

		return this.userMapper.toDomain(userEntity);
	}

	@Override
	@Transactional
	public User updateLocation(UUID id, UserLocation userLocation) {
		var userEntity = this.userRepository.findById(id).orElseThrow(() -> new SotdException("User not found"));
		userEntity.setLocation(this.userMapper.toLocationEntity(userLocation));
		return this.userMapper.toDomain(userEntity);
	}

}
