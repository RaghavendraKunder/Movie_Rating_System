package com.movierating.movie_rating_backend.exception;


@SuppressWarnings("serial")
public class WatchlistNotFoundException extends RuntimeException {

    public WatchlistNotFoundException() {
        super("Watchlist entry not found.");
    }

    public WatchlistNotFoundException(String message) {
        super(message);
    }

}
