package com.movierating.movie_rating_backend.exception;

@SuppressWarnings("serial")
public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException() {
        super("Genre not found.");
    }

    public GenreNotFoundException(String message) {
        super(message);
    }

}