package com.movierating.movie_rating_backend.exception;


@SuppressWarnings("serial")
public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException() {
        super("Movie not found.");
    }

    public MovieNotFoundException(String message) {
        super(message);
    }

}