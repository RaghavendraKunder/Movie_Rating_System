package com.movierating.movie_rating_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movierating.movie_rating_backend.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    // ==========================
    // Find Genre
    // ==========================

    Optional<Genre> findByNameIgnoreCase(String name);

    // ==========================
    // Duplicate Check
    // ==========================

    boolean existsByNameIgnoreCase(String name);

    // ==========================
    // Search
    // ==========================

    List<Genre> findByNameContainingIgnoreCase(String keyword);

    // ==========================
    // Sorting
    // ==========================

    List<Genre> findAllByOrderByNameAsc();

}