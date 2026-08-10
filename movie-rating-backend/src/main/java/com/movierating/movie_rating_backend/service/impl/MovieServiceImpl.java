package com.movierating.movie_rating_backend.service.impl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.movierating.movie_rating_backend.dto.MovieRequest;
import com.movierating.movie_rating_backend.dto.MovieResponse;
import com.movierating.movie_rating_backend.entity.Genre;
import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.exception.GenreNotFoundException;
import com.movierating.movie_rating_backend.exception.MovieAlreadyExistsException;
import com.movierating.movie_rating_backend.exception.MovieNotFoundException;
import com.movierating.movie_rating_backend.repository.GenreRepository;
import com.movierating.movie_rating_backend.repository.MovieRepository;
import com.movierating.movie_rating_backend.service.MovieService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final GenreRepository genreRepository;

    /**
     * Add a new movie.
     */
    @Override
    public MovieResponse addMovie(MovieRequest request) {

        if (movieRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new MovieAlreadyExistsException(
                    "Movie already exists with title : " + request.getTitle()
            );
        }

        Movie movie = mapToEntity(request);

        movie.setCreatedAt(LocalDateTime.now());

        Movie savedMovie = movieRepository.save(movie);

        return mapToResponse(savedMovie);
    }

    /**
     * Update movie.
     */
    @Override
    public MovieResponse updateMovie(Long id, MovieRequest request) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + id
                        ));

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setLanguage(request.getLanguage());
        movie.setDuration(request.getDuration());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setTrailerUrl(request.getTrailerUrl());
        movie.setDirector(request.getDirector());
        movie.setCast(request.getCast());
        movie.setGenres(getGenres(request.getGenreIds()));

        movie.setUpdatedAt(LocalDateTime.now());

        Movie updatedMovie = movieRepository.save(movie);

        return mapToResponse(updatedMovie);

    }
    
    /**
     * Delete movie.
     */
    @Override
    public void deleteMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + id
                        ));

        movieRepository.delete(movie);

    }

    /**
     * Get movie by ID.
     */
    @Override
    public MovieResponse getMovieById(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + id
                        ));

        return mapToResponse(movie);

    }

    /**
     * Get all movies.
     */
    @Override
    public List<MovieResponse> getAllMovies() {

        return movieRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get all movies with pagination.
     */
    @Override
    public Page<MovieResponse> getAllMovies(Pageable pageable) {

        return movieRepository.findAll(pageable)
                .map(this::mapToResponse);

    }
    
    /**
     * Search movies by title.
     */
    @Override
    public List<MovieResponse> searchMovie(String keyword) {

        return movieRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get movies by genre.
     */
    @Override
    public List<MovieResponse> getMoviesByGenre(String genreName) {

        Genre genre = genreRepository.findByNameIgnoreCase(genreName)
                .orElseThrow(() ->
                        new GenreNotFoundException(
                                "Genre not found : " + genreName
                        ));

        return movieRepository.findByGenresContaining(genre)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get movies by language.
     */
    @Override
    public List<MovieResponse> getMoviesByLanguage(String language) {

        return movieRepository.findByLanguageIgnoreCase(language)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get top-rated movies.
     */
    @Override
    public List<MovieResponse> getTopRatedMovies() {

        return movieRepository.findTop10ByOrderByAverageRatingDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get latest movies.
     */
    @Override
    public List<MovieResponse> getLatestMovies() {

        return movieRepository.findTop10ByOrderByReleaseDateDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }
    /**
     * Convert MovieRequest DTO to Movie Entity.
     */
    private Movie mapToEntity(MovieRequest request) {

        return Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .duration(request.getDuration())
                .language(request.getLanguage())
                .posterUrl(request.getPosterUrl())
                .trailerUrl(request.getTrailerUrl())
                .director(request.getDirector())
                .cast(request.getCast())
                .genres(getGenres(request.getGenreIds()))
                .averageRating(0.0)
                .totalRatings(0L)
                .totalReviews(0L)
                .createdAt(LocalDateTime.now())
                .build();

    }

    /**
     * Convert Movie Entity to MovieResponse DTO.
     */
    private MovieResponse mapToResponse(Movie movie) {

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .duration(movie.getDuration())
                .language(movie.getLanguage())
                .genres(
                        movie.getGenres()
                                .stream()
                                .map(Genre::getName)
                                .collect(Collectors.toSet())
                )
                .director(movie.getDirector())
                .cast(movie.getCast())
                .posterUrl(movie.getPosterUrl())
                .trailerUrl(movie.getTrailerUrl())
                .averageRating(movie.getAverageRating())
                .totalRatings(movie.getTotalRatings())
                .totalReviews(movie.getTotalReviews())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .build();

    }

    /**
     * Fetch Genre entities from genre IDs.
     */
    private List<Genre> getGenres(java.util.Set<Long> genreIds) {

        return genreIds.stream()
                .map(id -> genreRepository.findById(id)
                        .orElseThrow(() ->
                                new GenreNotFoundException(
                                        "Genre not found with ID : " + id
                                )))
                .collect(Collectors.toList());

    }

}