package com.movierating.movie_rating_backend.service;

import com.movierating.movie_rating_backend.dto.AuthenticationResponse;
import com.movierating.movie_rating_backend.dto.LoginRequest;
import com.movierating.movie_rating_backend.dto.RegisterRequest;

public interface AuthService {

    /**
     * Register a new user.
     *
     * @param request Registration details
     * @return JWT token with authenticated user information
     */
    AuthenticationResponse register(RegisterRequest request);

    /**
     * Authenticate an existing user.
     *
     * @param request Login credentials
     * @return JWT token with authenticated user information
     */
    AuthenticationResponse login(LoginRequest request);

}