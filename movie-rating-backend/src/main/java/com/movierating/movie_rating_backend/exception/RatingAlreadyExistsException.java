package com.movierating.movie_rating_backend.exception;

@SuppressWarnings("serial")
public class RatingAlreadyExistsException extends RuntimeException {

    public RatingAlreadyExistsException() {
        super("You have already rated this movie.");
    }

    public RatingAlreadyExistsException(String message) {
        super(message);
    }

}