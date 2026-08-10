package com.movierating.movie_rating_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movierating.movie_rating_backend.dto.ChangePasswordRequest;
import com.movierating.movie_rating_backend.dto.UpdateUserRequest;
import com.movierating.movie_rating_backend.dto.UserResponse;
import com.movierating.movie_rating_backend.entity.User;
import com.movierating.movie_rating_backend.exception.UserNotFoundException;
import com.movierating.movie_rating_backend.repository.RatingRepository;
import com.movierating.movie_rating_backend.repository.ReviewRepository;
import com.movierating.movie_rating_backend.repository.UserRepository;
import com.movierating.movie_rating_backend.repository.WatchlistRepository;
import com.movierating.movie_rating_backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;
    private final WatchlistRepository watchlistRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Logged-in user's profile
     */
    @Override
    public UserResponse getProfile() {

        return mapToResponse(getCurrentUser());

    }

    /**
     * Get User By ID (Admin)
     */
    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with ID : " + id));

        return mapToResponse(user);

    }

    /**
     * Get All Users (Admin)
     */
    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }
    /**
     * Update Logged-in User Profile
     */
    @Override
    public UserResponse updateProfile(UpdateUserRequest request) {

        User user = getCurrentUser();

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setProfilePicture(request.getProfilePicture());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);

    }

    /**
     * Change Password
     */
    @Override
    public void changePassword(ChangePasswordRequest request) {

        User user = getCurrentUser();

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException(
                    "Current password is incorrect.");

        }

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            throw new IllegalArgumentException(
                    "New password and Confirm password do not match.");

        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        userRepository.save(user);

    }
    /**
     * Delete Logged-in User Account
     */
    @Override
    public void deleteAccount() {

        User user = getCurrentUser();

        userRepository.delete(user);

    }

    /**
     * Get Currently Logged-in User
     */
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found."));

    }

    /**
     * Convert User Entity to UserResponse DTO
     */
    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profilePicture(user.getProfilePicture())
                .role(user.getRole().name())
                .enabled(user.getEnabled())
                .totalRatings(ratingRepository.countByUser(user))
                .totalReviews(reviewRepository.countByUser(user))
                .watchlistCount(watchlistRepository.countByUser(user))
                .joinedAt(user.getCreatedAt())
                .build();

    }
}