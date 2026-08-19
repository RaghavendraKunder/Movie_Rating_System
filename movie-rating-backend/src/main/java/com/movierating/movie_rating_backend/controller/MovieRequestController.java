package com.movierating.movie_rating_backend.controller;

import java.util.List;

import com.movierating.movie_rating_backend.dto.MovieRequestAdminResponse;
import com.movierating.movie_rating_backend.dto.MovieRequestCreate;
import com.movierating.movie_rating_backend.dto.MovieRequestResponse;
import com.movierating.movie_rating_backend.entity.Genre;
import com.movierating.movie_rating_backend.entity.MovieRequestEntity;
import com.movierating.movie_rating_backend.service.MovieRequestService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie-requests")
@RequiredArgsConstructor
@CrossOrigin(
        origins = "http://localhost:4200",
        allowCredentials = "true"
)
public class MovieRequestController {

    private final MovieRequestService movieRequestService;


    // =========================================================
    // USER - CREATE REQUEST
    // =========================================================

    @PostMapping
    public ResponseEntity<MovieRequestResponse> createRequest(
            @Valid @RequestBody MovieRequestCreate request,
            Authentication authentication) {

        String userEmail = authentication.getName();

        MovieRequestEntity savedRequest =
                movieRequestService.createRequest(
                        request,
                        userEmail
                );

        MovieRequestResponse response = MovieRequestResponse.builder()
                .id(savedRequest.getId())
                .title(savedRequest.getTitle())
                .description(savedRequest.getDescription())
                .releaseDate(savedRequest.getReleaseDate())
                .duration(savedRequest.getDuration())
                .language(savedRequest.getLanguage())
                .director(savedRequest.getDirector())
                .cast(savedRequest.getCast())
                .posterUrl(savedRequest.getPosterUrl())
                .trailerUrl(savedRequest.getTrailerUrl())
                .genres(
                        savedRequest.getGenres()
                                .stream()
                                .map(Genre::getName)
                                .collect(java.util.stream.Collectors.toSet())
                )
                .status(savedRequest.getStatus().name())
                .requestedAt(savedRequest.getRequestedAt())
                .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }


    // =========================================================
    // ADMIN - GET ALL REQUESTS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<MovieRequestAdminResponse>> getAllRequests() {

        return ResponseEntity.ok(
                movieRequestService.getAllRequests()
        );
    }


    // =========================================================
    // ADMIN - GET SINGLE REQUEST
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<MovieRequestAdminResponse> getRequestById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                movieRequestService.getRequestById(id)
        );
    }


    // =========================================================
    // ADMIN - APPROVE
    // =========================================================

    @PutMapping("/{id}/approve")
    public ResponseEntity<MovieRequestAdminResponse> approveRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                movieRequestService.approveRequest(id)
        );
    }


    // =========================================================
    // ADMIN - REJECT
    // =========================================================

    @PutMapping("/{id}/reject")
    public ResponseEntity<MovieRequestAdminResponse> rejectRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                movieRequestService.rejectRequest(id)
        );
    }
}