package com.maison.mabs.sotd.infrastructure.adapter.out.persistence;

import com.maison.mabs.sotd.application.port.out.UserJpaPort;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.mapper.UserMapper;
import com.maison.mabs.sotd.infrastructure.adapter.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
		var userEntity = this.userMapper.toEntity(user);
		var savedEntity = this.userRepository.save(userEntity);
		return this.userMapper.toDomain(savedEntity);
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
