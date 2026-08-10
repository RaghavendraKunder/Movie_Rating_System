package com.movierating.movie_rating_backend.controller;

import com.movierating.movie_rating_backend.dto.GenreRequest;
import com.movierating.movie_rating_backend.dto.GenreResponse;
import com.movierating.movie_rating_backend.service.GenreService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@CrossOrigin(
	    origins = "http://localhost:4200",
	    allowCredentials = "true"
	)
public class GenreController {

    private final GenreService genreService;

    /**
     * Add Genre
     * ADMIN Only
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenreResponse> addGenre(
            @Valid @RequestBody GenreRequest request) {

        GenreResponse response = genreService.addGenre(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get All Genres
     */
    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenres() {

        return ResponseEntity.ok(
                genreService.getAllGenres());
    }

    /**
     * Get Genre By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> getGenreById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                genreService.getGenreById(id));
    }

    /**
     * Update Genre
     * ADMIN Only
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenreResponse> updateGenre(
            @PathVariable Long id,
            @Valid @RequestBody GenreRequest request) {

        return ResponseEntity.ok(
                genreService.updateGenre(id, request));
    }

    /**
     * Delete Genre
     * ADMIN Only
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteGenre(
            @PathVariable Long id) {

        genreService.deleteGenre(id);

        return ResponseEntity.ok("Genre deleted successfully.");
    }

}