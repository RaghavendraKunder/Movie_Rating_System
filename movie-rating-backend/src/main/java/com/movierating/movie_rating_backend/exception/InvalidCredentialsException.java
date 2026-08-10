package com.movierating.movie_rating_backend.exception;


@SuppressWarnings("serial")
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

}