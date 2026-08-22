package com.movierating.movie_rating_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.movierating.movie_rating_backend.dto.RatingRequest;
import com.movierating.movie_rating_backend.dto.RatingResponse;
import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.entity.Rating;
import com.movierating.movie_rating_backend.entity.User;
import com.movierating.movie_rating_backend.entity.Genre;
import com.movierating.movie_rating_backend.exception.MovieNotFoundException;
import com.movierating.movie_rating_backend.exception.RatingNotFoundException;
import com.movierating.movie_rating_backend.exception.UserNotFoundException;
import com.movierating.movie_rating_backend.repository.MovieRepository;
import com.movierating.movie_rating_backend.repository.RatingRepository;
import com.movierating.movie_rating_backend.repository.UserRepository;
import com.movierating.movie_rating_backend.service.RatingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    /**
     * Rate Movie
     * Adds new rating or updates existing one.
     */
    @Override
    public RatingResponse rateMovie(RatingRequest request) {

        User user = getCurrentUser();

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : "
                                        + request.getMovieId()));

        Rating rating = ratingRepository
                .findByUserAndMovie(user, movie)
                .orElse(
                        Rating.builder()
                                .user(user)
                                .movie(movie)
                                .build()
                );

        rating.setRating(request.getRating());

        Rating savedRating = ratingRepository.save(rating);

        updateMovieStatistics(movie);

        return mapToResponse(savedRating);
    }

    /**
     * Update Rating
     */
    @Override
    public RatingResponse updateRating(
            Long movieId,
            RatingRequest request) {

        User user = getCurrentUser();

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        Rating rating = ratingRepository
                .findByUserAndMovie(user, movie)
                .orElseThrow(() ->
                        new RatingNotFoundException(
                                "Rating not found."));

        rating.setRating(request.getRating());

        Rating updatedRating = ratingRepository.save(rating);

        updateMovieStatistics(movie);

        return mapToResponse(updatedRating);
    }
    /**
     * Delete Rating
     */
    @Override
    public void deleteRating(Long movieId) {

        User user = getCurrentUser();

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        Rating rating = ratingRepository
                .findByUserAndMovie(user, movie)
                .orElseThrow(() ->
                        new RatingNotFoundException(
                                "Rating not found."));

        ratingRepository.delete(rating);

        updateMovieStatistics(movie);

    }

    /**
     * Get all ratings of a movie.
     */
    @Override
    public List<RatingResponse> getMovieRatings(Long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        return ratingRepository.findByMovie(movie)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get ratings of currently logged-in user.
     */
    @Override
    public List<RatingResponse> getUserRatings() {

        User user = getCurrentUser();

        return ratingRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }
    /**
     * Get average rating of a movie.
     */
    @Override
    public Double getAverageRating(Long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        Double average = ratingRepository.findAverageRatingByMovie(movie);

        return average == null ? 0.0 : average;

    }

    /**
     * Get currently logged-in user.
     */
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found."));

    }

    /**
     * Update movie statistics after rating changes.
     */
    private void updateMovieStatistics(Movie movie) {

        Double average =
                ratingRepository.findAverageRatingByMovie(movie);

        Long total =
                ratingRepository.countByMovie(movie);

        movie.setAverageRating(
                average == null ? 0.0 : average
        );

        movie.setTotalRatings(total);

        movieRepository.save(movie);

    }
    
    /* Convert Rating Entity to RatingResponse DTO.*/
    private RatingResponse mapToResponse(Rating rating) {
        Movie movie = rating.getMovie();
        Integer releaseYear = null;
        if (movie.getReleaseDate() != null) {
            releaseYear = movie.getReleaseDate()
                            .getYear();
        }
        List<String> genres = movie.getGenres()
                        .stream()
                        .map(Genre::getName)
                        .collect(Collectors.toList());

        return RatingResponse.builder()
                .ratingId(rating.getId())
                .movieId(movie.getId())
                .movieTitle(movie.getTitle())
                .posterUrl(movie.getPosterUrl())
                .releaseYear(releaseYear)
                .genres(genres)
                .userId(rating.getUser().getId())
                .userName(rating.getUser().getFullName())
                .rating(rating.getRating())
                .averageRating(movie.getAverageRating())
                .totalRatings(movie.getTotalRatings())
                .ratedAt(rating.getCreatedAt())

                .build();
    }
}