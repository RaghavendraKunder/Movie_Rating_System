package com.movierating.movie_rating_backend.exception;


@SuppressWarnings("serial")
public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException() {
        super("Review not found.");
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }

}