package com.movierating.movie_rating_backend.exception;

@SuppressWarnings("serial")
public class MovieAlreadyExistsException extends RuntimeException {

    public MovieAlreadyExistsException() {
        super("Movie already exists.");
    }

    public MovieAlreadyExistsException(String message) {
        super(message);
    }

}