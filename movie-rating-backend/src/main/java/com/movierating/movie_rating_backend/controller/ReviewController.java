package com.movierating.movie_rating_backend.controller;

import com.movierating.movie_rating_backend.dto.ReviewRequest;
import com.movierating.movie_rating_backend.dto.ReviewResponse;
import com.movierating.movie_rating_backend.service.ReviewService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(
	    origins = "http://localhost:4200",
	    allowCredentials = "true"
	)
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Add Review
     */
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewResponse> addReview(
            @Valid @RequestBody ReviewRequest request) {

        ReviewResponse response = reviewService.addReview(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update Review
     */
    @PutMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request) {

        return ResponseEntity.ok(
                reviewService.updateReview(reviewId, request));
    }

    /**
     * Delete Review
     */
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok("Review deleted successfully.");
    }

    /**
     * Get Reviews of a Movie
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getMovieReviews(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                reviewService.getMovieReviews(movieId));
    }

    /**
     * Get Logged-in User Reviews
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ReviewResponse>> getUserReviews() {

        return ResponseEntity.ok(
                reviewService.getUserReviews());
    }

    /**
     * Get Single Review
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(
            @PathVariable Long reviewId) {

        return ResponseEntity.ok(
                reviewService.getReviewById(reviewId));
    }

}