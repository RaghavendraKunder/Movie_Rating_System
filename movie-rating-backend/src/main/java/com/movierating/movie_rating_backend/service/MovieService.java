package com.movierating.movie_rating_backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.movierating.movie_rating_backend.dto.MovieRequest;
import com.movierating.movie_rating_backend.dto.MovieResponse;

public interface MovieService {

    /**
     * Add a new movie (Admin only).
     */
    MovieResponse addMovie(MovieRequest request);

    /**
     * Update an existing movie (Admin only).
     */
    MovieResponse updateMovie(Long id, MovieRequest request);

    /**
     * Delete a movie (Admin only).
     */
    void deleteMovie(Long id);

    /**
     * Get movie by ID.
     */
    MovieResponse getMovieById(Long id);

    /**
     * Get all movies.
     */
    List<MovieResponse> getAllMovies();

    /**
     * Get all movies with pagination.
     */
    Page<MovieResponse> getAllMovies(Pageable pageable);

    /**
     * Search movies by title.
     */
    List<MovieResponse> searchMovie(String keyword);

    /**
     * Get movies by genre.
     */
    List<MovieResponse> getMoviesByGenre(String genre);

    /**
     * Get movies by language.
     */
    List<MovieResponse> getMoviesByLanguage(String language);

    /**
     * Get top-rated movies.
     */
    List<MovieResponse> getTopRatedMovies();

    /**
     * Get latest released movies.
     */
    List<MovieResponse> getLatestMovies();

}