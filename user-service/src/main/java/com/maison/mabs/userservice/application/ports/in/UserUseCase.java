package com.maison.mabs.userservice.application.ports.in;

public interface UserUseCase {

    void createUser();

    void updateUser();

    void deleteUser();

    void addCollection();

    void addWishlist();
}
