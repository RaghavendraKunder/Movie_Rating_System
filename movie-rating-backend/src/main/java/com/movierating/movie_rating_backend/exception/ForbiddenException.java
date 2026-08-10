package com.movierating.movie_rating_backend.exception;

@SuppressWarnings("serial")
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Access denied.");
    }

    public ForbiddenException(String message) {
        super(message);
    }

}
