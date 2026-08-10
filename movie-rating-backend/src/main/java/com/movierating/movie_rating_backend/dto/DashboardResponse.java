package com.movierating.movie_rating_backend.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    // Overall Statistics
    private Long totalMovies;

    private Long totalUsers;

    private Long totalRatings;

    private Long totalReviews;

    private Double averageRating;

    // Featured Movies
    private MovieResponse featuredMovie;

    private MovieResponse topRatedMovie;

    private MovieResponse mostReviewedMovie;

    // Movie Lists
    private List<MovieResponse> latestMovies;

    private List<MovieResponse> trendingMovies;

    private List<MovieResponse> recommendedMovies;

}