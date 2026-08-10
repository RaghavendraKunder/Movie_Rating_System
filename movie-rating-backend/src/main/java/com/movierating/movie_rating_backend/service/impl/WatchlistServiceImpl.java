package com.movierating.movie_rating_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.movierating.movie_rating_backend.dto.WatchlistResponse;
import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.entity.User;
import com.movierating.movie_rating_backend.entity.Watchlist;
import com.movierating.movie_rating_backend.exception.MovieNotFoundException;
import com.movierating.movie_rating_backend.exception.UserNotFoundException;
import com.movierating.movie_rating_backend.repository.MovieRepository;
import com.movierating.movie_rating_backend.repository.UserRepository;
import com.movierating.movie_rating_backend.repository.WatchlistRepository;
import com.movierating.movie_rating_backend.service.WatchlistService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    /**
     * Add Movie To Watchlist
     */
    @Override
    public WatchlistResponse addToWatchlist(Long movieId) {

        User user = getCurrentUser();

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        Watchlist watchlist = watchlistRepository
                .findByUserAndMovie(user, movie)
                .orElse(
                        Watchlist.builder()
                                .user(user)
                                .movie(movie)
                                .build()
                );

        Watchlist savedWatchlist =
                watchlistRepository.save(watchlist);

        return mapToResponse(savedWatchlist);

    }

    /**
     * Remove Movie From Watchlist
     */
    @Override
    public void removeFromWatchlist(Long movieId) {

        User user = getCurrentUser();

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        watchlistRepository.findByUserAndMovie(user, movie)
                .ifPresent(watchlistRepository::delete);

    }
    /**
     * Get Logged-in User Watchlist
     */
    @Override
    public List<WatchlistResponse> getWatchlist() {

        User user = getCurrentUser();

        return watchlistRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /**
     * Check Movie Exists In Watchlist
     */
    @Override
    public boolean isMovieInWatchlist(Long movieId) {

        User user = getCurrentUser();

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        return watchlistRepository.existsByUserAndMovie(user, movie);

    }

    /**
     * Clear Entire Watchlist
     */
    @Override
    public void clearWatchlist() {

        User user = getCurrentUser();

        List<Watchlist> watchlist =
                watchlistRepository.findByUser(user);

        watchlistRepository.deleteAll(watchlist);

    }

    /**
     * Get Watchlist Count
     */
    @Override
    public Long getWatchlistCount() {

        User user = getCurrentUser();

        return watchlistRepository.countByUser(user);

    }
    /**
     * Get Currently Logged-in User
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
     * Convert Watchlist Entity to DTO
     */
    private WatchlistResponse mapToResponse(Watchlist watchlist) {

        Movie movie = watchlist.getMovie();

        Integer releaseYear = null;

        if (movie.getReleaseDate() != null) {
            releaseYear = movie.getReleaseDate().getYear();
        }

        return WatchlistResponse.builder()
                .watchlistId(watchlist.getId())
                .movieId(movie.getId())
                .movieTitle(movie.getTitle())
                .posterUrl(movie.getPosterUrl())
                .language(movie.getLanguage())
                .averageRating(movie.getAverageRating())
                .duration(movie.getDuration())
                .releaseYear(releaseYear)
                .addedAt(watchlist.getAddedAt())
                .build();

    }

}