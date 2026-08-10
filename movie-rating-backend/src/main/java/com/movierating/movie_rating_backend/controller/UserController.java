package com.movierating.movie_rating_backend.controller;


import com.movierating.movie_rating_backend.dto.ChangePasswordRequest;
import com.movierating.movie_rating_backend.dto.UpdateUserRequest;
import com.movierating.movie_rating_backend.dto.UserResponse;
import com.movierating.movie_rating_backend.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(
	    origins = "http://localhost:4200",
	    allowCredentials = "true"
	)
public class UserController {

    private final UserService userService;

    /**
     * Get Logged-in User Profile
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getProfile() {

        return ResponseEntity.ok(
                userService.getProfile());
    }

    /**
     * Update Profile
     */
    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateProfile(
            @Valid @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(
                userService.updateProfile(request));
    }

    /**
     * Change Password
     */
    @PutMapping("/change-password")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(request);

        return ResponseEntity.ok("Password changed successfully.");
    }

    /**
     * Delete Account
     */
    @DeleteMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteAccount() {

        userService.deleteAccount();

        return ResponseEntity.ok("Account deleted successfully.");
    }

}