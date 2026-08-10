package com.movierating.movie_rating_backend.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.entity.Review;
import com.movierating.movie_rating_backend.entity.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // ==========================
    // User & Movie
    // ==========================

    Optional<Review> findByUserAndMovie(User user, Movie movie);

    boolean existsByUserAndMovie(User user, Movie movie);

    // ==========================
    // User Reviews
    // ==========================

    List<Review> findByUser(User user);

    // ==========================
    // Movie Reviews
    // ==========================

    List<Review> findByMovie(Movie movie);

    long countByMovie(Movie movie);

    long countByUser(User user);

    // ==========================
    // Spoiler Filter
    // ==========================

    List<Review> findByMovieAndContainsSpoilerFalse(Movie movie);

    List<Review> findByMovieAndContainsSpoilerTrue(Movie movie);

}