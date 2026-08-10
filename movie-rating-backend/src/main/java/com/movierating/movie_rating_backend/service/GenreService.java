package com.movierating.movie_rating_backend.service;


import java.util.List;

import com.movierating.movie_rating_backend.dto.GenreRequest;
import com.movierating.movie_rating_backend.dto.GenreResponse;

public interface GenreService {

    /**
     * Add a new genre (Admin only).
     *
     * @param request Genre details
     * @return Created genre
     */
    GenreResponse addGenre(GenreRequest request);

    /**
     * Update an existing genre (Admin only).
     *
     * @param id Genre ID
     * @param request Updated genre details
     * @return Updated genre
     */
    GenreResponse updateGenre(Long id, GenreRequest request);

    /**
     * Delete a genre (Admin only).
     *
     * @param id Genre ID
     */
    void deleteGenre(Long id);

    /**
     * Get genre by ID.
     *
     * @param id Genre ID
     * @return Genre details
     */
    GenreResponse getGenreById(Long id);

    /**
     * Get all genres.
     *
     * @return List of genres
     */
    List<GenreResponse> getAllGenres();

    /**
     * Search genres by name.
     *
     * @param keyword Genre name
     * @return Matching genres
     */
    List<GenreResponse> searchGenres(String keyword);

}