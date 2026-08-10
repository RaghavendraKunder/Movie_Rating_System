package com.movierating.movie_rating_backend.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movierating.movie_rating_backend.entity.Genre;
import com.movierating.movie_rating_backend.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // ==========================
    // Find Movie
    // ==========================

    Optional<Movie> findByTitleIgnoreCase(String title);

    // ==========================
    // Duplicate Check
    // ==========================

    boolean existsByTitleIgnoreCase(String title);

    boolean existsByTitleIgnoreCaseAndReleaseDate(
            String title,
            LocalDate releaseDate
    );

    // ==========================
    // Search
    // ==========================

    List<Movie> findByTitleContainingIgnoreCase(String keyword);

    List<Movie> findByDirectorContainingIgnoreCase(String director);

    List<Movie> findByCastContainingIgnoreCase(String actor);

    // ==========================
    // Genre
    // ==========================

    List<Movie> findByGenresContaining(Genre genre);

    // ==========================
    // Language
    // ==========================

    List<Movie> findByLanguageIgnoreCase(String language);

    // ==========================
    // Release Date
    // ==========================

    List<Movie> findByReleaseDateBetween(
            LocalDate start,
            LocalDate end
    );

    // ==========================
    // Pagination
    // ==========================

    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    /**
     * Get Top Rated Movies
     */
    List<Movie> findTop10ByOrderByAverageRatingDesc();

    /**
     * Get Latest Movies
     */
    List<Movie> findTop10ByOrderByReleaseDateDesc();
}