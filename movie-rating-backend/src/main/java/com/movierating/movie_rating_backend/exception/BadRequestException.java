package com.movierating.movie_rating_backend.exception;


@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("Bad request.");
    }

    public BadRequestException(String message) {
        super(message);
    }

}