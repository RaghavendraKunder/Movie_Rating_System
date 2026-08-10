package com.movierating.movie_rating_backend.exception;

@SuppressWarnings("serial")
public class GenreAlreadyExistsException extends RuntimeException {

    public GenreAlreadyExistsException() {
        super("Genre already exists.");
    }

    public GenreAlreadyExistsException(String message) {
        super(message);
    }

}