package com.movierating.movie_rating_backend.controller;

import com.movierating.movie_rating_backend.dto.RatingRequest;
import com.movierating.movie_rating_backend.dto.RatingResponse;
import com.movierating.movie_rating_backend.service.RatingService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
@CrossOrigin(
	    origins = "http://localhost:4200",
	    allowCredentials = "true"
	)
public class RatingController {

    private final RatingService ratingService;

    /**
     * Rate a movie
     */
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<RatingResponse> rateMovie(
            @Valid @RequestBody RatingRequest request) {

        return ResponseEntity.ok(
                ratingService.rateMovie(request));
    }

    /**
     * Update Rating
     */
    @PutMapping("/{movieId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<RatingResponse> updateRating(
            @PathVariable Long movieId,
            @Valid @RequestBody RatingRequest request) {

        return ResponseEntity.ok(
                ratingService.updateRating(movieId, request));
    }

    /**
     * Delete Rating
     */
    @DeleteMapping("/{movieId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteRating(
            @PathVariable Long movieId) {

        ratingService.deleteRating(movieId);

        return ResponseEntity.ok("Rating deleted successfully.");
    }

    /**
     * Get Ratings of a Movie
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<RatingResponse>> getMovieRatings(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                ratingService.getMovieRatings(movieId));
    }

    /**
     * Get Logged-in User Ratings
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<RatingResponse>> getUserRatings() {

        return ResponseEntity.ok(
                ratingService.getUserRatings());
    }

    /**
     * Average Rating of Movie
     */
    @GetMapping("/average/{movieId}")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                ratingService.getAverageRating(movieId));
    }

}
