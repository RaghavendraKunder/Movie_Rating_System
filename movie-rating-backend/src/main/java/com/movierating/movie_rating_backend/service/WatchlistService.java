package com.movierating.movie_rating_backend.service;

import java.util.List;

import com.movierating.movie_rating_backend.dto.WatchlistResponse;

public interface WatchlistService {

    /**
     * Add Movie
     */
    WatchlistResponse addToWatchlist(Long movieId);

    /**
     * Remove Movie
     */
    void removeFromWatchlist(Long movieId);

    /**
     * Logged-in User Watchlist
     */
    List<WatchlistResponse> getWatchlist();

    /**
     * Check Movie Exists
     */
    boolean isMovieInWatchlist(Long movieId);

    /**
     * Clear Watchlist
     */
    void clearWatchlist();

    /**
     * Watchlist Count
     */
    Long getWatchlistCount();

}