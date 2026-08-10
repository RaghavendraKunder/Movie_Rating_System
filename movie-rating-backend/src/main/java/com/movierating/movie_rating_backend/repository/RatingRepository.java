package com.movierating.movie_rating_backend.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.entity.Rating;
import com.movierating.movie_rating_backend.entity.User;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    // ==========================
    // User & Movie
    // ==========================

    Optional<Rating> findByUserAndMovie(User user, Movie movie);

    boolean existsByUserAndMovie(User user, Movie movie);

    // ==========================
    // User Ratings
    // ==========================

    List<Rating> findByUser(User user);

    // ==========================
    // Movie Ratings
    // ==========================

    List<Rating> findByMovie(Movie movie);

    long countByMovie(Movie movie);

    long countByUser(User user);

    // ==========================
    // Average Rating
    // ==========================

    @Query("""
            SELECT AVG(r.rating)
            FROM Rating r
            WHERE r.movie = :movie
            """)
    Double findAverageRatingByMovie(Movie movie);

}