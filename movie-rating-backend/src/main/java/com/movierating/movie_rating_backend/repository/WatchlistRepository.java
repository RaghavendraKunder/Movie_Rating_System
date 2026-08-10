package com.movierating.movie_rating_backend.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.entity.User;
import com.movierating.movie_rating_backend.entity.Watchlist;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    // ==========================
    // User & Movie
    // ==========================

    Optional<Watchlist> findByUserAndMovie(User user, Movie movie);

    boolean existsByUserAndMovie(User user, Movie movie);

    // ==========================
    // User Watchlist
    // ==========================

    List<Watchlist> findByUser(User user);

    long countByUser(User user);

    // ==========================
    // Movie Watchlists
    // ==========================

    List<Watchlist> findByMovie(Movie movie);

    long countByMovie(Movie movie);

}