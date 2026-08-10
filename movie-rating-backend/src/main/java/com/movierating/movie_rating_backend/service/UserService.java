package com.movierating.movie_rating_backend.service;

import java.util.List;

import com.movierating.movie_rating_backend.dto.ChangePasswordRequest;
import com.movierating.movie_rating_backend.dto.UpdateUserRequest;
import com.movierating.movie_rating_backend.dto.UserResponse;

public interface UserService {

    /**
     * Logged-in user's profile
     */
    UserResponse getProfile();

    /**
     * Admin
     */
    UserResponse getUserById(Long id);

    /**
     * Admin
     */
    List<UserResponse> getAllUsers();

    /**
     * Update profile
     */
    UserResponse updateProfile(UpdateUserRequest request);

    /**
     * Change password
     */
    void changePassword(ChangePasswordRequest request);

    /**
     * Delete own account
     */
    void deleteAccount();

}