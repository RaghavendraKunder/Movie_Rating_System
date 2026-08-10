package com.movierating.movie_rating_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.movierating.movie_rating_backend.dto.DashboardResponse;
import com.movierating.movie_rating_backend.dto.MovieResponse;
import com.movierating.movie_rating_backend.dto.UserResponse;
import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.entity.User;
import com.movierating.movie_rating_backend.exception.MovieNotFoundException;
import com.movierating.movie_rating_backend.exception.UserNotFoundException;
import com.movierating.movie_rating_backend.repository.MovieRepository;
import com.movierating.movie_rating_backend.repository.RatingRepository;
import com.movierating.movie_rating_backend.repository.ReviewRepository;
import com.movierating.movie_rating_backend.repository.UserRepository;
import com.movierating.movie_rating_backend.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private final ReviewRepository reviewRepository;

    private final RatingRepository ratingRepository;

    /**
     * Get all users.
     */
    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get user by ID.
     */
    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with ID : " + id
                        ));

        return mapToUserResponse(user);

    }

    /**
     * Delete user.
     */
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with ID : " + id
                        ));

        userRepository.delete(user);

    }

    /**
     * Get all movies.
     */
    @Override
    public List<MovieResponse> getAllMovies() {

        return movieRepository.findAll()
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());

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
     * Dashboard statistics.
     */
    @Override
    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalMovies(movieRepository.count())
                .totalReviews(reviewRepository.count())
                .totalRatings(ratingRepository.count())
                .build();

    }

    /**
     * Total users.
     */
    @Override
    public Long totalUsers() {

        return userRepository.count();

    }

    /**
     * Total movies.
     */
    @Override
    public Long totalMovies() {

        return movieRepository.count();

    }

    /**
     * Total reviews.
     */
    @Override
    public Long totalReviews() {

        return reviewRepository.count();

    }

    /**
     * Total ratings.
     */
    @Override
    public Long totalRatings() {

        return ratingRepository.count();

    }

    /**
     * Convert User Entity -> UserResponse DTO
     */
    private UserResponse mapToUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profilePicture(user.getProfilePicture())
                .role(user.getRole().name())
                .enabled(user.getEnabled())
                .totalRatings((long) user.getRatings().size())
                .totalReviews((long) user.getReviews().size())
                .watchlistCount((long) user.getWatchlist().size())
                .joinedAt(user.getCreatedAt())
                .build();

    }

    /**
     * Convert Movie Entity -> MovieResponse DTO
     */
    private MovieResponse mapToMovieResponse(Movie movie) {

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .duration(movie.getDuration())
                .language(movie.getLanguage())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .posterUrl(movie.getPosterUrl())
                .trailerUrl(movie.getTrailerUrl())
                .averageRating(movie.getAverageRating())
                .totalRatings(movie.getTotalRatings())
                .totalReviews(movie.getTotalReviews())
                .genres(
                        movie.getGenres()
                                .stream()
                                .map(genre -> genre.getName())
                                .collect(Collectors.toSet())
                )
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .build();

    }

}