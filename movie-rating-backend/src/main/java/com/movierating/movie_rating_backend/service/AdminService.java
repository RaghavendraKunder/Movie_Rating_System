package com.movierating.movie_rating_backend.service;

import java.util.List;

import com.movierating.movie_rating_backend.dto.DashboardResponse;
import com.movierating.movie_rating_backend.dto.MovieResponse;
import com.movierating.movie_rating_backend.dto.UserResponse;

public interface AdminService {

    /**
     * User Management
     */
    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUser(Long id);

    /**
     * Movie Management
     */
    List<MovieResponse> getAllMovies();

    void deleteMovie(Long id);

    /**
     * Dashboard
     */
    DashboardResponse getDashboard();

    /**
     * Statistics
     */
    Long totalUsers();

    Long totalMovies();

    Long totalReviews();

    Long totalRatings();

}