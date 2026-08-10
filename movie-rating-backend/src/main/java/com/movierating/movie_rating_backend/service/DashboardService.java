package com.movierating.movie_rating_backend.service;

import java.util.List;

import com.movierating.movie_rating_backend.dto.DashboardResponse;
import com.movierating.movie_rating_backend.dto.MovieResponse;

public interface DashboardService {

    DashboardResponse getDashboardStats();

    List<MovieResponse> getLatestMovies();

    List<MovieResponse> getTopRatedMovies();

    List<MovieResponse> getTrendingMovies();

    List<MovieResponse> getRecommendedMovies();

}