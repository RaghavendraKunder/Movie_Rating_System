package com.movierating.movie_rating_backend.service;

import java.util.List;

import com.movierating.movie_rating_backend.dto.RatingRequest;
import com.movierating.movie_rating_backend.dto.RatingResponse;

public interface RatingService {

    /**
     * Add or update the logged-in user's rating for a movie.
     */
    RatingResponse rateMovie(RatingRequest request);

    /**
     * Update an existing rating.
     *
     * @param movieId Movie ID
     * @param request Updated rating
     * @return Updated rating
     */
    RatingResponse updateRating(Long movieId, RatingRequest request);

    /**
     * Delete the logged-in user's rating for a movie.
     *
     * @param movieId Movie ID
     */
    void deleteRating(Long movieId);

    /**
     * Get all ratings for a movie.
     *
     * @param movieId Movie ID
     * @return List of ratings
     */
    List<RatingResponse> getMovieRatings(Long movieId);

    /**
     * Get all ratings of the currently logged-in user.
     *
     * @return List of ratings
     */
    List<RatingResponse> getUserRatings();

    /**
     * Get average rating of a movie.
     *
     * @param movieId Movie ID
     * @return Average rating
     */
    Double getAverageRating(Long movieId);

}