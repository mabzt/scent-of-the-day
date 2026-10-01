package com.maison.mabs.userservice.application.ports.in;

import com.maison.mabs.userservice.domain.model.User;
import com.maison.mabs.userservice.domain.model.dto.CreateUserRequest;

public interface CreateProfileUseCase {

    User createUserProfile(CreateUserRequest createUserRequest);
}
