package com.movierating.movie_rating_backend.exception;


@SuppressWarnings("serial")
public class ReviewAlreadyExistsException extends RuntimeException {

    public ReviewAlreadyExistsException() {
        super("You have already reviewed this movie.");
    }

    public ReviewAlreadyExistsException(String message) {
        super(message);
    }

}