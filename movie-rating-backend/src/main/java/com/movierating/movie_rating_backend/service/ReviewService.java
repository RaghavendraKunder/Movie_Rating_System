package com.movierating.movie_rating_backend.service;

import java.util.List;

import com.movierating.movie_rating_backend.dto.ReviewRequest;
import com.movierating.movie_rating_backend.dto.ReviewResponse;

public interface ReviewService {

    /**
     * Add Review
     */
    ReviewResponse addReview(ReviewRequest request);

    /**
     * Update Review
     */
    ReviewResponse updateReview(Long reviewId, ReviewRequest request);

    /**
     * Delete Review
     */
    void deleteReview(Long reviewId);

    /**
     * Get Review by ID
     */
    ReviewResponse getReviewById(Long reviewId);

    /**
     * Get Reviews of a Movie
     */
    List<ReviewResponse> getMovieReviews(Long movieId);

    /**
     * Get Logged-in User Reviews
     */
    List<ReviewResponse> getUserReviews();

    /**
     * Get Non Spoiler Reviews
     */
    List<ReviewResponse> getNonSpoilerReviews(Long movieId);

    /**
     * Get Spoiler Reviews
     */
    List<ReviewResponse> getSpoilerReviews(Long movieId);

}