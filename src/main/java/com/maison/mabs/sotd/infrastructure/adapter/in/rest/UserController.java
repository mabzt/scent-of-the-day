package com.maison.mabs.sotd.infrastructure.adapter.in.rest;

import com.maison.mabs.sotd.application.port.in.UserPort;
import com.maison.mabs.sotd.domain.model.User;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CollectionRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CreateUserRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "User Controller", description = "User management endpoints")
public class UserController {

	private final UserPort userPort;

	@PostMapping(path = "/users", version = "1.0")
	public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
		return new ResponseEntity<>(this.userPort.createUserProfile(createUserRequest), HttpStatus.CREATED);

	}

	@PatchMapping(path = "/users/{id}", version = "1.0")
	public ResponseEntity<User> updateUser(@PathVariable UUID id,
			@RequestBody @Valid CollectionRequest collectionRequest) {
		return new ResponseEntity<>(this.userPort.addCollection(id, collectionRequest), HttpStatus.OK);

	}

}
