package com.movierating.movie_rating_backend.exception;

@SuppressWarnings("serial")
public class RatingNotFoundException extends RuntimeException {

    public RatingNotFoundException() {
        super("Rating not found.");
    }

    public RatingNotFoundException(String message) {
        super(message);
    }

}
