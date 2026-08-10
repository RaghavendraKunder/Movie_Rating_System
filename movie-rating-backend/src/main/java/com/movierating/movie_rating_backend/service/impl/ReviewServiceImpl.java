package com.movierating.movie_rating_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.movierating.movie_rating_backend.dto.ReviewRequest;
import com.movierating.movie_rating_backend.dto.ReviewResponse;
import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.entity.Rating;
import com.movierating.movie_rating_backend.entity.Review;
import com.movierating.movie_rating_backend.entity.User;
import com.movierating.movie_rating_backend.exception.MovieNotFoundException;
import com.movierating.movie_rating_backend.exception.ReviewNotFoundException;
import com.movierating.movie_rating_backend.exception.UserNotFoundException;
import com.movierating.movie_rating_backend.repository.MovieRepository;
import com.movierating.movie_rating_backend.repository.RatingRepository;
import com.movierating.movie_rating_backend.repository.ReviewRepository;
import com.movierating.movie_rating_backend.repository.UserRepository;
import com.movierating.movie_rating_backend.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    /**
     * Add Review
     */
    @Override
    public ReviewResponse addReview(ReviewRequest request) {

        User user = getCurrentUser();

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : "
                                        + request.getMovieId()));

        Review review = reviewRepository
                .findByUserAndMovie(user, movie)
                .orElse(
                        Review.builder()
                                .user(user)
                                .movie(movie)
                                .build()
                );

        review.setTitle(request.getTitle());
        review.setReview(request.getReview());
        review.setContainsSpoiler(request.getContainsSpoiler());

        Review savedReview = reviewRepository.save(review);

        updateMovieStatistics(movie);

        return mapToResponse(savedReview);

    }

    /**
     * Update Review
     */
    @Override
    public ReviewResponse updateReview(
            Long reviewId,
            ReviewRequest request) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewNotFoundException(
                                "Review not found with ID : "
                                        + reviewId));

        review.setTitle(request.getTitle());
        review.setReview(request.getReview());
        review.setContainsSpoiler(request.getContainsSpoiler());

        Review updatedReview = reviewRepository.save(review);

        updateMovieStatistics(review.getMovie());

        return mapToResponse(updatedReview);

    }
    /**
     * Delete Review
     */
    @Override
    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewNotFoundException(
                                "Review not found with ID : " + reviewId));

        Movie movie = review.getMovie();

        reviewRepository.delete(review);

        updateMovieStatistics(movie);

    }

    /**
     * Get Review By ID
     */
    @Override
    public ReviewResponse getReviewById(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewNotFoundException(
                                "Review not found with ID : " + reviewId));

        return mapToResponse(review);

    }

    /**
     * Get Reviews Of Movie
     */
    @Override
    public List<ReviewResponse> getMovieReviews(Long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        return reviewRepository.findByMovie(movie)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get Logged-in User Reviews
     */
    @Override
    public List<ReviewResponse> getUserReviews() {

        User user = getCurrentUser();

        return reviewRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }
    /**
     * Get Non-Spoiler Reviews
     */
    @Override
    public List<ReviewResponse> getNonSpoilerReviews(Long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        return reviewRepository.findByMovieAndContainsSpoilerFalse(movie)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    /**
     * Get Spoiler Reviews
     */
    @Override
    public List<ReviewResponse> getSpoilerReviews(Long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "Movie not found with ID : " + movieId));

        return reviewRepository.findByMovieAndContainsSpoilerTrue(movie)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

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
     * Update Movie Review Statistics
     */
    private void updateMovieStatistics(Movie movie) {

        long totalReviews = reviewRepository.countByMovie(movie);

        movie.setTotalReviews(totalReviews);

        movieRepository.save(movie);

    }
    /**
     * Convert Review Entity to ReviewResponse DTO.
     */
    private ReviewResponse mapToResponse(Review review) {

        Double userRating = ratingRepository
                .findByUserAndMovie(
                        review.getUser(),
                        review.getMovie())
                .map(Rating::getRating)
                .orElse(null);

        User currentUser = null;

        try {
            currentUser = getCurrentUser();
        } catch (Exception e) {
            // User not logged in
        }

        boolean editable = currentUser != null
                && currentUser.getId().equals(review.getUser().getId());

        return ReviewResponse.builder()
                .reviewId(review.getId())
                .movieId(review.getMovie().getId())
                .movieTitle(review.getMovie().getTitle())
                .userId(review.getUser().getId())
                .userName(review.getUser().getFullName())
                .title(review.getTitle())
                .review(review.getReview())
                .containsSpoiler(review.getContainsSpoiler())
                .likes(review.getLikes())
                .dislikes(review.getDislikes())
                .userRating(userRating)
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .editable(editable)
                .build();

    }

}