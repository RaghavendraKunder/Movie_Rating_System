package com.movierating.movie_rating_backend.exception;


@SuppressWarnings("serial")
public class MovieAlreadyInWatchlistException extends RuntimeException {

    public MovieAlreadyInWatchlistException() {
        super("Movie is already in your watchlist.");
    }

    public MovieAlreadyInWatchlistException(String message) {
        super(message);
    }

}