package com.maison.mabs.sotd.infrastructure.adapter.out.persistence;

import com.maison.mabs.sotd.application.port.out.UserJpaPort;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.web.exception.SotdException;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.mapper.UserMapper;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdaptor implements UserJpaPort {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	@Override
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

}
