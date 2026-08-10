package com.movierating.movie_rating_backend.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movierating.movie_rating_backend.dto.AuthenticationResponse;
import com.movierating.movie_rating_backend.dto.LoginRequest;
import com.movierating.movie_rating_backend.dto.RegisterRequest;
import com.movierating.movie_rating_backend.entity.Role;
import com.movierating.movie_rating_backend.entity.User;
import com.movierating.movie_rating_backend.exception.BadRequestException;
import com.movierating.movie_rating_backend.exception.InvalidCredentialsException;
import com.movierating.movie_rating_backend.exception.UserAlreadyExistsException;
import com.movierating.movie_rating_backend.repository.UserRepository;
import com.movierating.movie_rating_backend.config.JwtService;
import com.movierating.movie_rating_backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServicelmpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user
     */
    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        if (request == null) {
            throw new BadRequestException("Registration request cannot be null.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email is already registered."
            );
        }

        User user = buildUser(request);

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    /**
     * Creates User entity from RegisterRequest
     */
    private User buildUser(RegisterRequest request) {

    	return User.builder()
    	        .fullName(request.getFullName())
    	        .email(request.getEmail())
    	        .password(passwordEncoder.encode(request.getPassword()))
    	        .phone(request.getPhone())
    	        .profilePicture(null)
    	        .role(request.getRole())
    	        .enabled(true)
    	        .createdAt(LocalDateTime.now())
    	        .build();
    }
    /**
     * Authenticate user and generate JWT token.
     */
    @Override
    public AuthenticationResponse login(LoginRequest request) {

        if (request == null) {
            throw new BadRequestException(
                    "Login request cannot be null."
            );
        }

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        } catch (Exception ex) {

            throw new InvalidCredentialsException(
                    "Invalid email or password."
            );

        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password."
                        ));

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();

    }


}