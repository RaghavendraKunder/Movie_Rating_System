package com.movierating.movie_rating_backend.controller;

import com.movierating.movie_rating_backend.dto.DashboardResponse;
import com.movierating.movie_rating_backend.dto.MovieResponse;
import com.movierating.movie_rating_backend.service.DashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Dashboard Statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<DashboardResponse> getDashboardStats() {

        return ResponseEntity.ok(
                dashboardService.getDashboardStats());
    }

    /**
     * Latest Movies
     */
    @GetMapping("/latest")
    public ResponseEntity<List<MovieResponse>> getLatestMovies() {

        return ResponseEntity.ok(
                dashboardService.getLatestMovies());
    }

    /**
     * Top Rated Movies
     */
    @GetMapping("/top-rated")
    public ResponseEntity<List<MovieResponse>> getTopRatedMovies() {

        return ResponseEntity.ok(
                dashboardService.getTopRatedMovies());
    }

    /**
     * Trending Movies
     */
    @GetMapping("/trending")
    public ResponseEntity<List<MovieResponse>> getTrendingMovies() {

        return ResponseEntity.ok(
                dashboardService.getTrendingMovies());
    }

    /**
     * Recommended Movies
     */
    @GetMapping("/recommended")
    public ResponseEntity<List<MovieResponse>> getRecommendedMovies() {

        return ResponseEntity.ok(
                dashboardService.getRecommendedMovies());
    }

}