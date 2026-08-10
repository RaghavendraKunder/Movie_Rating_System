package com.movierating.movie_rating_backend.controller;

import com.movierating.movie_rating_backend.dto.DashboardResponse;
import com.movierating.movie_rating_backend.dto.MovieResponse;
import com.movierating.movie_rating_backend.dto.UserResponse;
import com.movierating.movie_rating_backend.service.AdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(
	    origins = "http://localhost:4200",
	    allowCredentials = "true"
	)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    /**
     * Get All Users
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers());
    }

    /**
     * Get User By ID
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.getUserById(id));
    }

    /**
     * Delete User
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        adminService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully.");
    }

    /**
     * Get All Movies
     */
    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {

        return ResponseEntity.ok(
                adminService.getAllMovies());
    }

    /**
     * Delete Movie
     */
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<String> deleteMovie(
            @PathVariable Long id) {

        adminService.deleteMovie(id);

        return ResponseEntity.ok(
                "Movie deleted successfully.");
    }

    /**
     * Dashboard Statistics
     */
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {

        return ResponseEntity.ok(
                adminService.getDashboard());
    }

    /**
     * Total Users
     */
    @GetMapping("/users/count")
    public ResponseEntity<Long> totalUsers() {

        return ResponseEntity.ok(
                adminService.totalUsers());
    }

    /**
     * Total Movies
     */
    @GetMapping("/movies/count")
    public ResponseEntity<Long> totalMovies() {

        return ResponseEntity.ok(
                adminService.totalMovies());
    }

    /**
     * Total Reviews
     */
    @GetMapping("/reviews/count")
    public ResponseEntity<Long> totalReviews() {

        return ResponseEntity.ok(
                adminService.totalReviews());
    }

    /**
     * Total Ratings
     */
    @GetMapping("/ratings/count")
    public ResponseEntity<Long> totalRatings() {

        return ResponseEntity.ok(
                adminService.totalRatings());
    }

}