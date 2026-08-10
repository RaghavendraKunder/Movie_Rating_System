package com.movierating.movie_rating_backend.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.movierating.movie_rating_backend.dto.DashboardResponse;
import com.movierating.movie_rating_backend.dto.MovieResponse;
import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.repository.MovieRepository;
import com.movierating.movie_rating_backend.repository.RatingRepository;
import com.movierating.movie_rating_backend.repository.ReviewRepository;
import com.movierating.movie_rating_backend.repository.UserRepository;
import com.movierating.movie_rating_backend.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;

    /**
     * Dashboard Statistics
     */
    @Override
    public DashboardResponse getDashboardStats() {

        List<Movie> latestMovies = movieRepository
                .findTop10ByOrderByReleaseDateDesc();

        List<Movie> topRatedMovies = movieRepository
                .findTop10ByOrderByAverageRatingDesc();

        Movie featuredMovie = latestMovies.isEmpty()
                ? null
                : latestMovies.get(0);

        Movie topRatedMovie = topRatedMovies.isEmpty()
                ? null
                : topRatedMovies.get(0);

        Movie mostReviewedMovie = movieRepository.findAll()
                .stream()
                .max(Comparator.comparing(Movie::getTotalReviews))
                .orElse(null);

        Double averageRating = movieRepository.findAll()
                .stream()
                .mapToDouble(Movie::getAverageRating)
                .average()
                .orElse(0.0);

        return DashboardResponse.builder()
                .totalMovies(movieRepository.count())
                .totalUsers(userRepository.count())
                .totalRatings(ratingRepository.count())
                .totalReviews(reviewRepository.count())
                .averageRating(averageRating)

                .featuredMovie(mapToMovieResponse(featuredMovie))
                .topRatedMovie(mapToMovieResponse(topRatedMovie))
                .mostReviewedMovie(mapToMovieResponse(mostReviewedMovie))

                .latestMovies(
                        latestMovies.stream()
                                .map(this::mapToMovieResponse)
                                .collect(Collectors.toList())
                )

                .trendingMovies(
                        topRatedMovies.stream()
                                .map(this::mapToMovieResponse)
                                .collect(Collectors.toList())
                )

                .recommendedMovies(
                        topRatedMovies.stream()
                                .map(this::mapToMovieResponse)
                                .collect(Collectors.toList())
                )

                .build();
    }
    /**
     * Get Latest Movies
     */
    @Override
    public List<MovieResponse> getLatestMovies() {

        return movieRepository.findTop10ByOrderByReleaseDateDesc()
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get Top Rated Movies
     */
    @Override
    public List<MovieResponse> getTopRatedMovies() {

        return movieRepository.findTop10ByOrderByAverageRatingDesc()
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get Trending Movies
     */
    @Override
    public List<MovieResponse> getTrendingMovies() {

        // Currently using Top Rated movies as Trending.
        // You can later replace this with a real trending algorithm.

        return movieRepository.findTop10ByOrderByAverageRatingDesc()
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get Recommended Movies
     */
    @Override
    public List<MovieResponse> getRecommendedMovies() {

        // Currently using Top Rated movies as Recommendations.
        // Later this can be replaced with personalized recommendations.

        return movieRepository.findTop10ByOrderByAverageRatingDesc()
                .stream()
                .map(this::mapToMovieResponse)
                .collect(Collectors.toList());

    }
    /**
     * Convert Movie Entity to MovieResponse DTO
     */
    private MovieResponse mapToMovieResponse(Movie movie) {

        if (movie == null) {
            return null;
        }

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .duration(movie.getDuration())
                .language(movie.getLanguage())
                .posterUrl(movie.getPosterUrl())
                .trailerUrl(movie.getTrailerUrl())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .averageRating(movie.getAverageRating())
                .totalRatings(movie.getTotalRatings())
                .totalReviews(movie.getTotalReviews())
                .genres(
                        movie.getGenres()
                                .stream()
                                .map(genre -> genre.getName())
                                .collect(Collectors.toSet())
                )
                .build();

    }

}