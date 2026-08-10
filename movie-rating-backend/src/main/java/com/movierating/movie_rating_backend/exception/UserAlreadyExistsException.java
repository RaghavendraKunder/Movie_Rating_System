package com.movierating.movie_rating_backend.exception;


@SuppressWarnings("serial")
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("User already exists.");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}