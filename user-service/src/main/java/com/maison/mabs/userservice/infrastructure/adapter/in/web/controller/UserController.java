package com.maison.mabs.userservice.infrastructure.adapter.in.web.controller;

import com.maison.mabs.userservice.application.ports.in.CreateProfileUseCase;
import com.maison.mabs.userservice.domain.model.User;
import com.maison.mabs.userservice.domain.model.dto.CreateUserRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "User Controller", description = "User management endpoints")
public class UserController {

	private final CreateProfileUseCase createProfileUseCase;

	@PostMapping(path = "/users", version = "1.0")
	public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
		return new ResponseEntity<>(this.createProfileUseCase.createUserProfile(createUserRequest), HttpStatus.CREATED);

	}

}
