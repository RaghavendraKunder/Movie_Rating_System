package com.movierating.movie_rating_backend.exception;

@SuppressWarnings("serial")
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Unauthorized access.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}